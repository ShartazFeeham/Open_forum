package com.open.forum.review.application.useCase.reaction;

import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

public interface DeleteReactionUseCase {
    /**
     * Deletes a reaction by its ID.
     *
     * @param reactionId the ID of the reaction to be deleted
     */
    void deleteReaction(@NonNull @Positive Long reactionId);
}
