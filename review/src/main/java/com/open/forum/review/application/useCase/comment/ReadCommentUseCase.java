package com.open.forum.review.application.useCase.comment;

import com.open.forum.review.application.dto.comment.CommentReadDTO;
import org.springframework.lang.NonNull;

public interface ReadCommentUseCase {
    /**
     * Reads a comment by its ID.
     *
     * @param commentId the ID of the comment to be read
     * @return the data transfer object containing the details of the comment
     */
    CommentReadDTO read(@NonNull Long commentId);
}
