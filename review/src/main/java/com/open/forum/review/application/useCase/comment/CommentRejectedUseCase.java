package com.open.forum.review.application.useCase.comment;

import com.open.forum.review.domain.model.comment.Comment;

public interface CommentRejectedUseCase {
    /**
     * Rejects a comment.
     *
     * @param comment the comment to be rejected
     */
    void reject(Comment comment);
}
