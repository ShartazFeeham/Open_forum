package com.open.forum.review.infrastructure.events;

import com.open.forum.review.domain.events.comment.CommentEvent;
import com.open.forum.review.domain.events.publisher.CommentEventPublisher;
import com.open.forum.review.infrastructure.events.core.AbstractProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CommentEventPublisherImpl extends AbstractProducer<String, String, CommentEvent> implements CommentEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(CommentEventPublisherImpl.class);

    protected CommentEventPublisherImpl(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public void publish(CommentEvent event) {
        this.send(event, (sendResult) -> {
            log.info("Comment event sent successfully: {}", sendResult);
        }, (throwable) -> {
            log.error("Comment error sending event!", throwable);
        });
    }

    @Override
    public String getKey(CommentEvent event) {
        return getTopic(event) + event.getEventData().getCommentId();
    }

    @Override
    public String getValue(CommentEvent event) throws Exception {
        return objectMapper.writeValueAsString(event);
    }
}
