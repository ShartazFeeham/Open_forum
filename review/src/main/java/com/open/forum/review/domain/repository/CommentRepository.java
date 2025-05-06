package com.open.forum.review.domain.repository;

import com.open.forum.review.domain.model.comment.Comment;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing comments in the review system.
 * This interface defines methods for saving, retrieving, updating, and deleting comments.
 */
public interface CommentRepository {

    /**
     * Saves a comment to the repository.
     *
     * @param comment the comment to save
     */
    void saveComment(@Validated Comment comment);

    /**
     * Finds a comment by its ID.
     *
     * @param commentId the ID of the comment
     * @return the found comment, or null if not found in an optional
     */
    Optional<Comment> findCommentById(@NonNull @Positive Long commentId);

    /**
     * Finds all comments for a specific post.
     *
     * @param postId the ID of the post
     * @param page the page number for pagination
     * @param size the number of comments per page
     * @return a list of comments for the post
     */
    List<Comment> findCommentsByPostId(@NonNull @Positive Long postId, @Min(0) int page, @Min(1) int size);

    /**
     * Finds all comments made by a specific user.
     *
     * @param userId the ID of the user
     * @return a list of comments made by the user
     */
    List<Comment> findCommentsByUserId(@NonNull @Positive Long userId, @Min(0) int page, @Min(1) int size);

    /**
     * Updates an existing comment.
     *
     * @param comment the comment to update
     */
    void updateComment(@Validated Comment comment);

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to delete
     */
    void deleteComment(@NonNull @Positive Long commentId);
}
