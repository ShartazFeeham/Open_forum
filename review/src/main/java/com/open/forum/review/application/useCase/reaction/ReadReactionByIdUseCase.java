package com.open.forum.review.application.useCase.reaction;

import com.open.forum.review.application.dto.reaction.ReactionReadDTO;

public interface ReadReactionByIdUseCase {
    /**
     * Reads a reaction by its ID.
     *
     * @param reactionId the ID of the reaction to be read
     * @return the data transfer object containing the details of the reaction
     */
    ReactionReadDTO readById(Long reactionId);
}
