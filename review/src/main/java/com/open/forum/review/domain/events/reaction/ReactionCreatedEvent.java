package com.open.forum.review.domain.events.reaction;

import com.open.forum.review.domain.events.Event;
import com.open.forum.review.domain.model.reaction.Reaction;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an event that is triggered when a reaction is created.
 * It extends the ReactionEvent class and contains the necessary information about the reaction.
 *
 * @see ReactionEvent
 */
@Getter @Setter
public class ReactionCreatedEvent extends ReactionEvent {

    public ReactionCreatedEvent(Reaction reaction) {
        super(reaction.getPostId(),  Event.COMMENT_CREATED, reaction.getUserId(), reaction);
    }

}
