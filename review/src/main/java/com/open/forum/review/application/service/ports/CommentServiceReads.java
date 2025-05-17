package com.open.forum.review.application.service.ports;

import com.open.forum.review.application.useCase.comment.ReadCommentUseCase;
import com.open.forum.review.application.useCase.comment.ReadCommentsByUserUseCase;
import com.open.forum.review.application.useCase.comment.ReadCommentsListUseCase;

public interface CommentServiceReads extends ReadCommentsByUserUseCase, ReadCommentsListUseCase, ReadCommentUseCase {
}
