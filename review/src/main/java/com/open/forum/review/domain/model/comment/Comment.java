package com.open.forum.review.domain.model.comment;

import com.open.forum.review.shared.validator.ObjectValidator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Represents a comment in the review system.
 * This class contains information about the comment, including its ID, post ID, user ID, status, and creation date.
 */
@Getter @Setter @Builder @AllArgsConstructor
public class Comment implements ObjectValidator {
    private final Long commentId;
    private final Long postId;
    @NotNull @Positive
    private final Long userId;
    private final boolean isReply;
    private final Long parentCommentId;
    List<CommentContent> contents;
    private final CommentStatus status;
    private final ZonedDateTime createdAt;

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

    @Override
    public String getEntityName() {
        return this.getClass().getName();
    }
}
