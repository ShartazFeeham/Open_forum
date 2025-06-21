package com.open.forum.review.application.useCase.comment;

import com.open.forum.review.application.dto.comment.CommentCreateDTO;
import com.open.forum.review.domain.model.comment.Comment;

public interface AddCommentUseCase {
    /**
     * Try to add a new comment, initially set to pending status. Eventually it is added after verification
     * from the analysis server.
     *
     * @param dto the data transfer object containing the details of the comment to be added
     */
    void create(CommentCreateDTO dto);

    /**
     * Save the comment after all validation and verification is passed.
     * @param comment the comment object
     */
    void create(Comment comment);
}
