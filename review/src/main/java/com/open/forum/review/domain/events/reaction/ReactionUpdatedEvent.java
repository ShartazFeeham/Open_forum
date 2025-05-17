package com.open.forum.review.domain.events.reaction;

import com.open.forum.review.domain.events.Event;
import com.open.forum.review.domain.model.reaction.Reaction;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an event that is triggered when a reaction is updated.
 * It extends the ReactionEvent class and contains the necessary information about the reaction.
 *
 * @see ReactionEvent
 */
@Getter @Setter
public class ReactionUpdatedEvent extends ReactionEvent {

    public ReactionUpdatedEvent(Reaction reaction) {
        super(reaction.getPostId(),  Event.REACTION_UPDATED, reaction.getUserId(), reaction);
    }

}
