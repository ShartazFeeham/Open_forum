package com.open.forum.review.infrastructure.events.core;

import com.open.forum.review.domain.events.BaseEvent;
import com.open.forum.review.domain.events.comment.CommentEvent;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Abstract class for producing events to Kafka.
 *
 * @param <K> the type of the event/producer-record-key
 * @param <V> the type of the event/producer-record value (it is a converted form of the object `O` being sent, i.e.: JSON string)
 * @param <E> the type of the object being sent
 */
@Slf4j
public abstract class AbstractProducer <@NotNull K, @NotNull V, @NotNull E extends BaseEvent<?>>
        implements EventProducer<K, V, E> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    protected AbstractProducer(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<SendResult<K, V>> send(E object, Consumer<SendResult<K, V>> onSuccess,
                                                    Consumer<Throwable> onFailure) {
        V value = null;
        try {
            value = getValue(object);
        } catch (Exception ignored) {
            log.error("Error processing value conversion!");
        }

        ProducerRecord<K, V> producerRecord = buildProducerRecord(object, value);
        return kafkaTemplate.send(producerRecord).whenComplete((result, exception) -> {
            if (exception != null) {
                onFailure.accept(exception);
            } else {
                onSuccess.accept(result);
            }
        });
    }

    public ProducerRecord<K, V> buildProducerRecord(E event, V value) {
        List<Header> recordHeaders = new ArrayList<>();

        String traceId =  UUID.randomUUID().toString().substring(0, 5);
        String spanId = UUID.randomUUID().toString().substring(0, 5);
        recordHeaders.add(new RecordHeader("trace-id", traceId.getBytes(StandardCharsets.UTF_8)));
        recordHeaders.add(new RecordHeader("span-id", spanId.getBytes(StandardCharsets.UTF_8)));

        String instanceId = UUID.randomUUID().toString().substring(0, 5);
        recordHeaders.add(new RecordHeader("event-source", "product-command-service".getBytes()));
        recordHeaders.add(new RecordHeader("event-source-instance-id", instanceId.getBytes(StandardCharsets.UTF_8)));

        return new ProducerRecord<>(getTopic(event), null, getKey(event), value, recordHeaders);
    }

    @Override
    public String getTopic(BaseEvent event) {
        return event.getEventType() + event.getEventName();
    }
}