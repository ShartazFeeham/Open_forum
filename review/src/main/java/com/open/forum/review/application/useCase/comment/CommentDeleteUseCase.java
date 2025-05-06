package com.open.forum.review.application.useCase.comment;

public interface CommentDeleteUseCase {
    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     */
    void delete(Long commentId);
}
