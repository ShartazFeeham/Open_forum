package com.open.forum.review.application.service;

import com.open.forum.review.application.dto.comment.CommentReadDTO;
import com.open.forum.review.application.useCase.comment.ReadCommentUseCase;
import com.open.forum.review.application.useCase.comment.ReadCommentsByUserUseCase;
import com.open.forum.review.application.useCase.comment.ReadCommentsListUseCase;
import jakarta.validation.constraints.Min;
import org.springframework.lang.NonNull;

import java.util.List;

public class CommentServiceReadsImpl implements ReadCommentsByUserUseCase, ReadCommentsListUseCase, ReadCommentUseCase {

    /**
     * Reads a comment by its ID.
     *
     * @param commentId the ID of the comment to be read
     * @return the data transfer object containing the details of the comment
     */
    @Override
    public CommentReadDTO read(@NonNull Long commentId) {
        return null;
    }

    /**
     * Retrieves a list of comments made by a user on a specific post.
     *
     * @param userId the ID of the user
     * @param page   the page number to retrieve (0-indexed)
     * @param size   the number of comments per page
     * @return a list of comments made by the user on the specified post
     */
    @Override
    public List<CommentReadDTO> readByUser(@NonNull Long userId, @Min(0) int page, @Min(1) int size) {
        return List.of();
    }

    /**
     * Retrieves a list of comments for a given post ID.
     *
     * @param postId the ID of the post to retrieve comments for
     * @param page   the page number to retrieve (0-indexed)
     * @param size   the number of comments per page
     * @return a list of comments for the specified post
     */
    @Override
    public List<CommentReadDTO> read(@NonNull Long postId, int page, int size) {
        return List.of();
    }
}
