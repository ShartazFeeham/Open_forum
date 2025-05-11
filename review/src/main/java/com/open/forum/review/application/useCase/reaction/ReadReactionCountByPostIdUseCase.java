package com.open.forum.review.application.useCase.reaction;

import com.open.forum.review.domain.model.reaction.ReactionType;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

import java.util.Map;

public interface ReadReactionCountByPostIdUseCase {
    /**
     * Retrieves the count of reactions for a given post ID.
     *
     * @param postId the ID of the post to retrieve reaction counts for
     * @return a map where the keys are reaction types and the values are their respective counts
     */
    Map<ReactionType, Integer> readReactionsCountByPostId(@NonNull @Positive Long postId);
}
