package com.open.forum.review.domain.model.comment;

import com.open.forum.review.shared.exception.EntityNotFoundException;
import com.open.forum.review.shared.exception.InvalidEntityException;
import com.open.forum.review.shared.helper.TokenExtractor;
import com.open.forum.review.shared.validator.ObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents a comment in the review system.
 * This class contains information about the comment, including its ID, post ID, user ID, status, and creation date.
 */
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Comment implements ObjectValidator {
    private Long commentId;
    private Long postId;
    @NotNull @Positive
    private Long userId;
    private boolean isReply;
    private Long parentCommentId;
    List<CommentContent> contents;
    private CommentStatus status;
    private ZonedDateTime createdAt;

    @Override
    public boolean validate() {
        boolean isInvalid = (status == null) // Invalid status
                || (contents == null || contents.isEmpty()) // Comment has no contents
                || (postId == null && parentCommentId == null) // Either postId (comment) or parentCommentId (reply) has to be present
                || (isReply && parentCommentId == null) // Reply must have a parent comment ID
                || (!isReply && postId == null) // Comment must have a post ID
        ;
        return !isInvalid;
    }

    public boolean isReadAllowed() {
        if (status == null) {
            throw new InvalidEntityException("Comment status is null, cannot determine read permission");
        } else if (status == CommentStatus.DELETED) {
            throw new EntityNotFoundException("Comment not found with ID: " + commentId);
        } else return status != CommentStatus.REJECTED && (status != CommentStatus.HIDDEN || TokenExtractor.matchUserId(userId));
    }

    @Override
    public String getEntityName() {
        return this.getClass().getName();
    }
}
