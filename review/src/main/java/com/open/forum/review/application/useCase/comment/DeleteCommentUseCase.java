package com.open.forum.review.application.useCase.comment;

public interface DeleteCommentUseCase {
    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     */
    void delete(Long commentId);
}
