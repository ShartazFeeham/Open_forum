package com.open.forum.review.application.service;

import com.open.forum.review.application.dto.comment.CommentCreateDTO;
import com.open.forum.review.application.useCase.comment.DeleteCommentUseCase;
import com.open.forum.review.application.useCase.comment.UpdateCommentUseCase;
import com.open.forum.review.application.useCase.comment.AddCommentUseCase;
import com.open.forum.review.domain.model.comment.Comment;

public class CommentServiceWritesImpl implements AddCommentUseCase, UpdateCommentUseCase, DeleteCommentUseCase {

    /**
     * Adds a new comment.
     *
     * @param dto the data transfer object containing the details of the comment to be added
     * @return the created comment
     */
    @Override
    public Comment create(CommentCreateDTO dto) {
        return null;
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     */
    @Override
    public void delete(Long commentId) {

    }

    /**
     * Updates an existing comment.
     *
     * @param dto the data transfer object containing the updated details of the comment
     * @return the updated comment
     */
    @Override
    public Comment update(CommentCreateDTO dto) {
        return null;
    }
}
