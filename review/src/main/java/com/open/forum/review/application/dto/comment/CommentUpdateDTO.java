package com.open.forum.review.application.dto.comment;

import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.shared.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
@Setter
@Builder
public record CommentUpdateDTO(Long commentId, String content) {

    private static final Logger log = LoggerFactory.getLogger(CommentUpdateDTO.class);

    public boolean validate(Long commentId, Comment comment) {
        return comment != null && comment.getCommentId() != null
                && comment.getCommentId().equals(commentId);
    }

    public void validateOrThrow(Long commentId, Comment comment) {
        if (!validate(commentId, comment)) {
            final var exception = new EntityNotFoundException("No record found for " + this.getClass().getName() + " with ID: " + commentId);
            log.error("Validation failed for {}", this.getClass().getName(), exception);
            throw exception;
        }
    }
}
