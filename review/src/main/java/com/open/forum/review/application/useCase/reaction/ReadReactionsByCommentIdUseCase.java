package com.open.forum.review.application.useCase.reaction;

import com.open.forum.review.domain.model.reaction.Reaction;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ReadReactionsByCommentIdUseCase {
    /**
     * Retrieves a list of reactions for a given comment ID.
     *
     * @param commentId the ID of the comment to retrieve reactions for
     * @return a list of reactions for the specified comment
     */
    List<Reaction> readReactionsByCommentId(@NonNull @Positive Long commentId);
}
