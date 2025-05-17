package com.open.forum.review.application.service.ports;

import com.open.forum.review.application.useCase.reaction.*;

public interface ReactionServiceReads extends ReadReactionsCountByCommentIdUseCase, ReadReactionByIdUseCase,
        ReadReactionByPostIdUseCase, ReadReactionCountByPostIdUseCase, ReadReactionsByCommentIdUseCase {
}
