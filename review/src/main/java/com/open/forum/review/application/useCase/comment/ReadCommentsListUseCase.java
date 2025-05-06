package com.open.forum.review.application.useCase.comment;

import com.open.forum.review.application.dto.comment.CommentReadDTO;
import jakarta.validation.constraints.Min;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ReadCommentsListUseCase {
    /**
     * Retrieves a list of comments for a given post ID.
     *
     * @param postId the ID of the post to retrieve comments for
     * @param page   the page number to retrieve (0-indexed)
     * @param size   the number of comments per page
     * @return a list of comments for the specified post
     */
    List<CommentReadDTO> read(@NonNull Long postId, @Min(0) int page, @Min(1) int size);
}
