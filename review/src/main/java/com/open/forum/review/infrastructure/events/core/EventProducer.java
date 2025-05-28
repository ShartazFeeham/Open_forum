package com.open.forum.review.infrastructure.events.core;

import com.open.forum.review.domain.events.BaseEvent;
import jakarta.validation.constraints.NotNull;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Interface for producing events to Kafka.
 *
 * @param <K> the type of the event/producer-record key
 * @param <V> the type of the event/producer-record value (it is a converted form of the object being sent, e.g., JSON string)
 * @param <E> the type of the event being sent
 */
public interface EventProducer <@NotNull K, @NotNull V, @NotNull E extends BaseEvent<?>> {
    /**
     * Sends an event to Kafka.
     *
     * @param event the event to be sent
     * @param onSuccess callback to be executed on successful send
     * @param onFailure callback to be executed on failure
     * @return a CompletableFuture that will complete with the result of the send operation
     */
    CompletableFuture<SendResult<K, V>> send(E event, Consumer<SendResult<K, V>> onSuccess, Consumer<Throwable> onFailure);

    /**
     * Gets the key for the event.
     *
     * @param event the event
     * @return the key
     */
    K getKey(E event);

    /**
     * Gets the value for the event.
     *
     * @param event the event
     * @return the value as a string
     * @throws Exception if there is an error during conversion
     */
    V getValue(E event) throws Exception;

    /**
     * Gets the topic for the event.
     *
     * @param event the event
     * @return the topic name
     */
    String getTopic(E event);
}