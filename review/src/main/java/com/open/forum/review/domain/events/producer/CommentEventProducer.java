package com.open.forum.review.domain.events.producer;


import com.open.forum.review.domain.events.comment.CommentEvent;

/**
 * This interface is responsible for publishing comment events in the system.
 * It provides a method to publish a CommentEvent.
 */
public interface CommentEventProducer {
    void publish(CommentEvent event);
}
