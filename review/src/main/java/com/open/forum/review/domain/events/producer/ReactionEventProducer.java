package com.open.forum.review.domain.events.producer;

import com.open.forum.review.domain.events.reaction.ReactionEvent;

/**
 * This class is responsible for publishing reaction events in the system.
 * It provides a method to publish a ReactionEvent.
 */
public interface ReactionEventProducer {
    void publish(ReactionEvent event);
}
