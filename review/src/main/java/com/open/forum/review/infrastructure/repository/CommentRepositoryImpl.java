package com.open.forum.review.infrastructure.repository;

import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.repository.CommentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    /**
     * Saves a comment to the repository.
     *
     * @param comment the comment to save
     */
    @Override
    public void saveComment(Comment comment) {

    }

    /**
     * Finds a comment by its ID.
     *
     * @param commentId the ID of the comment
     * @return the found comment, or null if not found in an optional
     */
    @Override
    public Optional<Comment> findCommentById(Long commentId) {
        return Optional.empty();
    }

    /**
     * Finds all comments for a specific post.
     *
     * @param postId the ID of the post
     * @param page   the page number for pagination
     * @param size   the number of comments per page
     * @return a list of comments for the post
     */
    @Override
    public List<Comment> findCommentsByPostId(Long postId, int page, int size) {
        return List.of();
    }

    /**
     * Finds all comments made by a specific user.
     *
     * @param userId the ID of the user
     * @param page
     * @param size
     * @return a list of comments made by the user
     */
    @Override
    public List<Comment> findCommentsByUserId(Long userId, int page, int size) {
        return List.of();
    }

    /**
     * Updates an existing comment.
     *
     * @param comment the comment to update
     */
    @Override
    public void updateComment(Comment comment) {

    }

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to delete
     */
    @Override
    public void deleteComment(Long commentId) {

    }
}
