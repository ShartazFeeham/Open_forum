package com.open.forum.review.application.service;

import com.open.forum.review.application.dto.comment.CommentReadDTO;
import com.open.forum.review.application.mapper.CommentMapper;
import com.open.forum.review.application.service.ports.CommentServiceReads;
import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.repository.CommentRepository;
import com.open.forum.review.shared.exception.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceReadsImpl implements CommentServiceReads {

    private final CommentRepository repository;
    private final Logger log = LoggerFactory.getLogger(CommentServiceReadsImpl.class);

    /**
     * Reads a comment by its ID.
     *
     * @param commentId the ID of the comment to be read
     * @return the data transfer object containing the details of the comment
     */
    @Override
    public CommentReadDTO read(@NonNull Long commentId) {
        log.info("Reading comment with ID: {}", commentId);
        Optional<Comment> commentOp = repository.findCommentById(commentId);
        commentOp.ifPresentOrElse(comment -> {
            log.info("Comment found: {}", comment);
            if (comment.isReadAllowed()) {
                log.info("Comment is readable");
            } else {
                log.error("Comment with ID {} is not readable", commentId);
                throw new EntityNotFoundException("Comment not found with ID: " + commentId);
            }
        }, () -> {
            log.error("Comment with ID {} not found", commentId);
            throw new EntityNotFoundException("Comment not found with ID: " + commentId);
        });
        return CommentMapper.toCommentReadDTO(commentOp.get());
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
        List<Comment> comments = repository.findCommentsByUserId(userId, page, size);
        log.info("Found {} comments for user ID: {}", comments.size(), userId);
        return comments.stream()
                .filter(Comment::isReadAllowed)
                .map(CommentMapper::toCommentReadDTO)
                .toList();
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
        List<Comment> comments = repository.findCommentsByPostId(postId, page, size);
        log.info("Found {} comments for post ID: {}", comments.size(), postId);
        return comments.stream()
                .filter(Comment::isReadAllowed)
                .map(CommentMapper::toCommentReadDTO)
                .toList();
    }
}
