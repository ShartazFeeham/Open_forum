package com.open.forum.review.application.useCase.reaction;

import com.open.forum.review.domain.model.reaction.Reaction;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ReadReactionByPostIdUseCase {
    /**
     * Retrieves a list of reactions for a given post ID.
     *
     * @param postId the ID of the post to retrieve reactions for
     * @param page   the page number to retrieve (0-indexed)
     * @param size   the number of reactions per page
     * @return a list of reactions for the specified post
     */
    List<Reaction> readReactionsByPostId(@NonNull @Positive Long postId, @Min(0) int page, @Min(1) int size);
}
