package com.open.forum.review.domain.events.reaction;

import com.open.forum.review.domain.events.Event;
import com.open.forum.review.domain.model.reaction.Reaction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an event that is triggered when a reaction is deleted.
 * It extends the ReactionEvent class and contains the necessary information about the reaction.
 *
 * @see ReactionEvent
 */
@Getter @Setter @Builder
public class ReactionDeletedEvent extends ReactionEvent {

    public ReactionDeletedEvent(Reaction reaction) {
        super(reaction.getPostId(),  Event.REACTION_DELETED, reaction.getUserId(), reaction);
    }

}
