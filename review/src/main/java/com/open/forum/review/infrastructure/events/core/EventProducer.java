package com.open.forum.review.infrastructure.events.core;

import com.open.forum.review.domain.events.BaseEvent;
import jakarta.validation.constraints.NotNull;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface EventProducer <@NotNull K, @NotNull V, @NotNull E extends BaseEvent<?>> {
    CompletableFuture<SendResult<K, V>> send(E event, Consumer<SendResult<K, V>> onSuccess, Consumer<Throwable> onFailure);
    K getKey(E event);
    V getValue(E event) throws Exception;
    String getTopic(E event);
}