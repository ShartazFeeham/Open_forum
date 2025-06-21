package com.open.forum.review.application.useCase.comment;

import com.open.forum.review.application.dto.comment.CommentUpdateDTO;
import com.open.forum.review.domain.model.comment.Comment;

public interface UpdateCommentUseCase {
    /**
     * Try to update an existing comment, initially set to pending status. Eventually it is updated after verification
     * from the analysis server.
     *
     * @param dto the data transfer object containing the updated details of the comment
     */
    void update(CommentUpdateDTO dto);

    /**
     * Update the comment after all validation and verification is passed.
     * @param comment the comment object
     */
    void update(Comment comment);
}
