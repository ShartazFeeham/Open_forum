package com.open.forum.review.infrastructure.events;

import com.open.forum.review.domain.events.publisher.ReactionEventPublisher;
import com.open.forum.review.domain.events.reaction.ReactionEvent;
import org.springframework.stereotype.Service;

@Service
public class ReactionEventPublisherImpl implements ReactionEventPublisher {
    /**
     * @param event the event to be published
     */
    @Override
    public void publish(ReactionEvent event) {

    }
}
