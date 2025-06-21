package com.open.forum.review.infrastructure.events.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.open.forum.review.application.service.ports.CommentServiceWrites;
import com.open.forum.review.domain.events.Event;
import com.open.forum.review.domain.events.comment.CommentEvent;
import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.model.comment.CommentStatus;
import com.open.forum.review.shared.enums.CommentAnalyzerMark;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

import static com.open.forum.review.shared.enums.CommentAnalyzerConstants.MARK;
import static com.open.forum.review.shared.enums.CommentAnalyzerConstants.SOURCE;

@Service
public class CommentAnalyzedEventConsumer {

    private final Logger log = LoggerFactory.getLogger(CommentAnalyzedEventConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CommentServiceWrites commentServiceWrites;

    public CommentAnalyzedEventConsumer(CommentServiceWrites commentServiceWrites) {
        this.commentServiceWrites = commentServiceWrites;
    }

    @KafkaListener(topics = "${local.kafka.properties.external.comment-analyzed-topic}",
            groupId = "${local.kafka.properties.external.comment-analyzed-listener-group}")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        log.info("Consumed message from comment analyzer service: Key: {}, Value: {}, Topic: {}, Partition: {}, " +
                "Offset: {}", record.key(), record.value(), record.topic(), record.partition(), record.offset());
        final String jsonValue = record.value();
        final CommentEvent commentEvent;
        final CommentAnalyzerMark commentAnalyzerMark;
        try {
            final Map<?, ?> eventData = objectMapper.readValue(jsonValue, Map.class);
            commentEvent = (CommentEvent) eventData.get(SOURCE);
            commentAnalyzerMark = (CommentAnalyzerMark) eventData.get(MARK);
        } catch (JsonProcessingException exception) {
            log.error("Failed to read comment event. Message: {}", exception.getMessage(), exception);
            return;
        }
        log.info("Comment event consumed: {}", commentEvent);
        callServiceLayer(commentEvent, commentAnalyzerMark);
    }

    private void callServiceLayer(CommentEvent commentEvent, CommentAnalyzerMark commentAnalyzerMark) {
        final String eventName = commentEvent.getEventName();
        final Consumer<Comment> serviceCallSupplier;
        final Comment comment = commentEvent.getEventData();

        if (eventName.equals(Event.COMMENT_CREATED.name())) {
            serviceCallSupplier = commentServiceWrites::create;
        } else if (eventName.equals(Event.COMMENT_UPDATED.name())) {
            serviceCallSupplier = commentServiceWrites::update;
        } else {
            log.error("Unknown comment event name: {}", eventName);
            return;
        }

        applyAction(eventName, commentAnalyzerMark, comment, serviceCallSupplier);
    }

    private void applyAction(String eventName, CommentAnalyzerMark commentAnalyzerMark,
                             Comment comment, Consumer<Comment> serviceCallSupplier) {
        if (commentAnalyzerMark == CommentAnalyzerMark.BAD) {
            log.info("Rejecting comment {}, because it was marked as {}, from analyzer service.",
                    comment, commentAnalyzerMark);
            commentServiceWrites.reject(comment);
        } else {
            log.info("Comment level is marked as {}, accepting the comment for: {}", commentAnalyzerMark, eventName);
            comment.setStatus(CommentStatus.PUBLISHED);
            serviceCallSupplier.accept(comment);
        }
    }
}
