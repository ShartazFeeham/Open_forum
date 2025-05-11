package com.open.forum.review.application.useCase.comment;

import com.open.forum.review.application.dto.comment.CommentReadDTO;
import jakarta.validation.constraints.Min;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ReadCommentsByUserUseCase {
    /**
     * Retrieves a list of comments made by a user on a specific post.
     *
     * @param userId the ID of the user
     * @param page   the page number to retrieve (0-indexed)
     * @param size   the number of comments per page
     * @return a list of comments made by the user on the specified post
     */
    List<CommentReadDTO> readByUser(@NonNull Long userId, @Min(0) int page, @Min(1) int size);
}
