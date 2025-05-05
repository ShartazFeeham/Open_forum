package com.open.forum.review.domain.events.publisher;

import org.yaml.snakeyaml.events.CommentEvent;

/**
 * This interface is responsible for publishing comment events in the system.
 * It provides a method to publish a CommentEvent.
 */
public interface CommentEventPublisher {
    void publish(CommentEvent event);
}
