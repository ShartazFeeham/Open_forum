package com.open.forum.review.application.service.ports;

import com.open.forum.review.application.useCase.comment.AddCommentUseCase;
import com.open.forum.review.application.useCase.comment.CommentRejectedUseCase;
import com.open.forum.review.application.useCase.comment.DeleteCommentUseCase;
import com.open.forum.review.application.useCase.comment.UpdateCommentUseCase;

public interface CommentServiceWrites extends AddCommentUseCase, UpdateCommentUseCase, DeleteCommentUseCase, CommentRejectedUseCase {
}
