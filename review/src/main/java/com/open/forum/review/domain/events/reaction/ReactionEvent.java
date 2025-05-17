package com.open.forum.review.domain.events.reaction;

import com.open.forum.review.domain.events.BaseEvent;
import com.open.forum.review.domain.events.Event;
import com.open.forum.review.domain.model.reaction.Reaction;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a comment event in the system.
 * It extends the BaseEvent class and provides additional information specific to reaction events.
 * The REACTION_EVENT constant is used to identify this type of event.
 */
@Getter @Setter
public class ReactionEvent extends BaseEvent<Reaction> {

    private static final String REACTION_EVENT = "REACTION_EVENT";

    public ReactionEvent(Long postId, Event event, Long userId, Reaction eventData) {
        super(postId, userId, event, eventData, REACTION_EVENT);
    }
}
