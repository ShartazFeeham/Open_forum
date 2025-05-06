package com.open.forum.review.application.useCase.reaction;

import com.open.forum.review.application.dto.reaction.ReactionCreateDTO;
import com.open.forum.review.domain.model.reaction.Reaction;

public interface CreateReactionUseCase {
    /**
     * Creates a new reaction.
     *
     * @param dto the data transfer object containing the details of the reaction to be created
     * @return the created reaction
     */
    Reaction create(ReactionCreateDTO dto);
}
