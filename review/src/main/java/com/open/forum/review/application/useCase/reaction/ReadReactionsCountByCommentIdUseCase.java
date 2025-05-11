package com.open.forum.review.application.useCase.reaction;

import com.open.forum.review.domain.model.reaction.ReactionType;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

import java.util.Map;

public interface ReadReactionsCountByCommentIdUseCase {
    /**
     * Retrieves the count of reactions for a given comment ID.
     *
     * @param postId the ID of the comment to retrieve reaction counts for
     * @return a map where the keys are reaction types and the values are their respective counts
     */
    Map<ReactionType, Integer> readReactionsCountByCommentId(@NonNull @Positive Long postId);
}
