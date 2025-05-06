package com.open.forum.review.application.useCase.comment;

import com.open.forum.review.application.dto.comment.CommentUpdateDTO;
import com.open.forum.review.domain.model.comment.Comment;

public interface UpdateCommentUseCase {
    /**
     * Updates an existing comment.
     *
     * @param dto the data transfer object containing the updated details of the comment
     */
    void update(CommentUpdateDTO dto);
}
