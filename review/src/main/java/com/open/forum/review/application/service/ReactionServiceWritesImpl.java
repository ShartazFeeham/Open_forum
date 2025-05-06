package com.open.forum.review.application.service;

import com.open.forum.review.application.dto.reaction.ReactionCreateDTO;
import com.open.forum.review.application.dto.reaction.ReactionUpdateDTO;
import com.open.forum.review.application.useCase.reaction.CreateReactionUseCase;
import com.open.forum.review.application.useCase.reaction.DeleteReactionUseCase;
import com.open.forum.review.application.useCase.reaction.UpdateReactionUseCase;
import com.open.forum.review.domain.model.reaction.Reaction;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceWritesImpl implements CreateReactionUseCase, DeleteReactionUseCase, UpdateReactionUseCase {

    /**
     * Creates a new reaction.
     *
     * @param dto the data transfer object containing the details of the reaction to be created
     * @return the created reaction
     */
    @Override
    public Reaction create(ReactionCreateDTO dto) {
        return null;
    }

    /**
     * Deletes a reaction by its ID.
     *
     * @param reactionId the ID of the reaction to be deleted
     */
    @Override
    public void deleteReaction(Long reactionId) {

    }

    /**
     * Updates an existing reaction.
     *
     * @param dto the data transfer object containing the updated details of the reaction
     * @return the updated reaction
     */
    @Override
    public Reaction update(ReactionUpdateDTO dto) {
        return null;
    }
}
