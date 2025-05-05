package com.open.forum.review.domain.events;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Base class for all events in the review service.
 * @param <T> The type of the event data.
 */
@Builder @Getter @Setter
public abstract class BaseEvent<T> {
    @NotNull
    private final Long postId;
    @NotNull
    private final Long userId;
    @NotBlank
    private final String eventName;
    private final ZonedDateTime publishedAt;
    @NotNull
    private final T eventData;
    private final String eventType ;
    private final String eventDescription;

    protected BaseEvent(Long postId, Long userId, Event event, T eventData, String eventType) {
        this.postId = postId;
        this.userId = userId;
        this.eventName = event.getTitle();
        this.publishedAt = ZonedDateTime.now();
        this.eventData = eventData;
        this.eventType = eventType;
        this.eventDescription = event.getDescription();
    }
}
