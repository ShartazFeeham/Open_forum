package com.open.forum.review.domain.model.comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Represents the content of a comment in the review system.
 * This class contains information about the comment's ID and its content.
 */
@Getter @Setter @AllArgsConstructor @Builder
public class CommentContent {
    @NotNull
    private final Long commentId;
    @NotEmpty
    @Max(value = 5000, message = "Comment must be less than 5000 characters")
    private final String content;
}
