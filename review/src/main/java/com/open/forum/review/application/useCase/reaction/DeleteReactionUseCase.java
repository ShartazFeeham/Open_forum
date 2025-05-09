package com.open.forum.review.application.useCase.reaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface DeleteReactionUseCase {
    /**
     * Deletes a reaction by its ID.
     *
     * @param reactionId the ID of the reaction to be deleted
     */
    void deleteReaction(@NotNull @Positive Long reactionId);
}
