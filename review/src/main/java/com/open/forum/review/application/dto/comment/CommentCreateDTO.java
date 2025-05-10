package com.open.forum.review.application.dto.comment;

import com.open.forum.review.shared.validator.ObjectValidator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public record CommentCreateDTO(Long commentId, Long postId, @NotNull Long userId, boolean isReply, Long parentCommentId,
                               @NotEmpty String content) implements ObjectValidator {
    /**
     * Validates the object.
     *
     * @return true if the object is valid, false otherwise
     */
    @Override
    public boolean validate() {
        boolean isInvalid = (content == null || content.isBlank()) // Comment has no contents
                || (postId == null && parentCommentId == null) // Either postId (comment) or parentCommentId (reply) has to be present
                || (isReply && parentCommentId == null) // Reply must have a parent comment ID
                || (!isReply && postId == null) // Comment must have a post ID
                ;
        return !isInvalid;
    }

    /**
     * Returns the name of the entity being validated.
     *
     * @return the name of the entity
     */
    @Override
    public String getEntityName() {
        return this.getClass().getName();
    }
}
