package com.open.forum.review.application.useCase.reaction;

import com.open.forum.review.application.dto.reaction.ReactionUpdateDTO;
import com.open.forum.review.domain.model.reaction.Reaction;

public interface UpdateReactionUseCase {
    /**
     * Updates an existing reaction.
     *
     * @param dto the data transfer object containing the updated details of the reaction
     * @return the updated reaction
     */
    Reaction update(ReactionUpdateDTO dto);
}
