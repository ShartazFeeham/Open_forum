package com.open.forum.review.infrastructure.events;

import com.open.forum.review.domain.events.comment.CommentEvent;
import com.open.forum.review.domain.events.publisher.CommentEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CommentEventPublisherImpl implements CommentEventPublisher {

    /**
     * @param event the event to publish
     */
    @Override
    public void publish(CommentEvent event) {

    }
}
