package com.open.forum.review.application.useCase.reaction;

import com.open.forum.review.application.dto.reaction.ReactionUpdateDTO;

public interface UpdateReactionUseCase {
    /**
     * Updates an existing reaction.
     *
     * @param dto the data transfer object containing the updated details of the reaction
     */
    void update(ReactionUpdateDTO dto);
}
