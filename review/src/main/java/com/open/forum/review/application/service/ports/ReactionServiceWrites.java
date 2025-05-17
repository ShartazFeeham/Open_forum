package com.open.forum.review.application.service.ports;

import com.open.forum.review.application.useCase.reaction.CreateReactionUseCase;
import com.open.forum.review.application.useCase.reaction.DeleteReactionUseCase;
import com.open.forum.review.application.useCase.reaction.UpdateReactionUseCase;

public interface ReactionServiceWrites extends CreateReactionUseCase, DeleteReactionUseCase, UpdateReactionUseCase {
}
