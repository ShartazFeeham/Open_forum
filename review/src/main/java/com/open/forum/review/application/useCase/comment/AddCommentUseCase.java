package com.open.forum.review.application.useCase.comment;

import com.open.forum.review.application.dto.comment.CommentCreateDTO;
import com.open.forum.review.domain.model.comment.Comment;

public interface AddCommentUseCase {
    /**
     * Adds a new comment.
     *
     * @param dto the data transfer object containing the details of the comment to be added
     * @return the created comment
     */
    Comment create(CommentCreateDTO dto);
}
