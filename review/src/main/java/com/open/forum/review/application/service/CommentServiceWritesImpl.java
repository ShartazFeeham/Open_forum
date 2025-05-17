package com.open.forum.review.application.service;

import com.open.forum.review.application.dto.comment.CommentCreateDTO;
import com.open.forum.review.application.dto.comment.CommentUpdateDTO;
import com.open.forum.review.application.mapper.CommentMapper;
import com.open.forum.review.application.service.ports.CommentServiceWrites;
import com.open.forum.review.domain.cache.PostPrivacyCache;
import com.open.forum.review.domain.cache.UserExistenceCache;
import com.open.forum.review.domain.events.comment.CommentCreatedEvent;
import com.open.forum.review.domain.events.comment.CommentDeletedEvent;
import com.open.forum.review.domain.events.comment.CommentUpdatedEvent;
import com.open.forum.review.domain.events.publisher.CommentEventPublisher;
import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.model.comment.CommentStatus;
import com.open.forum.review.domain.repository.CommentRepository;
import com.open.forum.review.shared.PostPrivacy;
import com.open.forum.review.shared.exception.IllegalRequestException;
import com.open.forum.review.shared.exception.TaskNotCompletableException;
import com.open.forum.review.shared.helper.TokenExtractor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service @RequiredArgsConstructor
public class CommentServiceWritesImpl implements CommentServiceWrites {

    private final CommentRepository repository;
    private final Logger log = LoggerFactory.getLogger(CommentServiceWritesImpl.class);
    private final CommentEventPublisher commentEventPublisher;
    private final PostPrivacyCache postPrivacyCache;
    private final UserExistenceCache userExistenceCache;

    /**
     * Adds a new comment.
     *
     * @param dto the data transfer object containing the details of the comment to be added
     */
    @Override
    public void create(CommentCreateDTO dto) {
        dto.validateOrThrow();
        Comment comment = CommentMapper.toComment(dto);
        validateUserExistence(comment);
        PostPrivacy postPrivacy = postPrivacyCache.getPostPrivacy(comment.getPostId());
        checkWhetherCommentIsAllowed(postPrivacy, comment);
        comment.setStatus(CommentStatus.PUBLISHED);
        try {
            repository.saveComment(comment);
            log.info("Comment saved successfully: {}", comment);
            publishCommentCreatedEvent(comment);
        } catch (Exception e) {
            log.error("Error saving comment: {}", e.getMessage());
            throw new TaskNotCompletableException("Cannot perform this action right now."
                    + (comment.isReply() ? "comment" : "post") + " may not exit! Or please try again later.");
        }
    }

    private void validateUserExistence(Comment comment) {
        Boolean isUserExist = userExistenceCache.isUserExist(comment.getUserId());
        if (isUserExist == null) {
            log.error("User existence check failed for userId: {}", comment.getUserId());
            throw new TaskNotCompletableException("Cannot perform this action right now, please try again later.");
        } else if (!isUserExist) {
            log.error("User does not exist: {}", comment.getUserId());
            throw new IllegalRequestException("User does not exist.");
        }
    }

    private void checkWhetherCommentIsAllowed(PostPrivacy postPrivacy, Comment comment) {
        if (postPrivacy == null) {
            log.error("Post privacy not found for postId: {}", comment.getPostId());
            throw new TaskNotCompletableException("Cannot perform this action right now, please try again later.");
        }
        if (postPrivacy == PostPrivacy.REVIEW_NOT_ALLOWED) {
            log.error("Commenting is not allowed on a private post: {}", comment.getPostId());
            throw new IllegalRequestException("Commenting is not allowed on a private post.");
        }
    }

    private void publishCommentCreatedEvent(Comment comment) {
        final CommentCreatedEvent event = new CommentCreatedEvent(comment);
        commentEventPublisher.publish(event);
        log.info("Comment created event published: {}", event);
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param commentId the ID of the comment to be deleted
     */
    @Override
    public void delete(Long commentId) {
        final var commentOptional = repository.findCommentById(commentId);
        commentOptional.ifPresentOrElse(
                comment -> {
                    try {
                        checkForUserRight(comment, "delete");
                        repository.deleteComment(commentId);
                        log.info("Comment deleted successfully: {}", comment);
                        publishCommentDeletedEvent(comment);
                    } catch (Exception e) {
                        log.error("Error deleting comment: {}, error: {}", comment, e.getMessage());
                        throw new TaskNotCompletableException("Cannot perform this action right now, please try again later.");
                    }
                }, () -> {
                    log.error("Comment not found with ID: {}", commentId);
                    throw new IllegalRequestException("Comment not found with ID: " + commentId);
                }
        );
    }

    private void publishCommentDeletedEvent(Comment comment) {
        final CommentDeletedEvent event = new CommentDeletedEvent(comment);
        commentEventPublisher.publish(event);
        log.info("Comment deleted event published: {}", event);
    }

    /**
     * Updates an existing comment.
     *
     * @param dto the data transfer object containing the updated details of the comment
     */
    @Override
    public void update(CommentUpdateDTO dto) {
        Optional<Comment> commentOp = repository.findCommentById(dto.commentId());
        commentOp.ifPresentOrElse(
                comment -> {
                    Comment updatedComment = CommentMapper.toComment(dto, comment);
                    try {
                        checkForUserRight(comment, "update");
                        repository.updateComment(updatedComment);
                        log.info("Comment updated successfully: {}", updatedComment);
                        publishCommentUpdatedEvent(updatedComment);
                    } catch (Exception e) {
                        log.error("Error updating comment: {}, error: {}", updatedComment, e.getMessage());
                        throw new TaskNotCompletableException("Cannot perform this action right now, please try again later.");
                    }
                }, () -> {
                    log.error("Update failed, comment not found with ID: {}", dto.commentId());
                    throw new IllegalRequestException("Comment not found with ID: " + dto.commentId());
                }
        );
    }

    private void publishCommentUpdatedEvent(Comment updatedComment) {
        final CommentUpdatedEvent event = new CommentUpdatedEvent(updatedComment);
        commentEventPublisher.publish(event);
        log.info("Comment updated event published: {}", event);
    }

    /**
     * Rejects a comment.
     *
     * @param comment the comment to be rejected
     */
    @Override
    public void reject(Comment comment) {
        comment.setStatus(CommentStatus.REJECTED);
        try {
            repository.updateComment(comment);
            log.info("Comment rejected successfully: {}", comment);
            publishCommentUpdatedEvent(comment);
        } catch (Exception e) {
            log.error("Error rejecting comment: {}, error: {}", comment, e.getMessage());
            throw new TaskNotCompletableException("Cannot perform this action right now, please try again later.");
        }
    }

    private void checkForUserRight(Comment comment, String action) {
        boolean matchUserId = TokenExtractor.matchUserId(comment.getUserId());
        if (!matchUserId) {
            log.error("User does not have permission to {} this comment: {}", action, comment.getUserId());
            throw new IllegalRequestException("You do not have permission to " + action + " this comment.");
        }
    }
}
