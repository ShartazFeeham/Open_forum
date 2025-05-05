package com.open.forum.review.domain.model.reaction;

import com.open.forum.review.domain.validator.ObjectValidator;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Reaction implements ObjectValidator {
    private Long reactionId;
    private Long postId;
    private Long commentId;
    @NotNull
    private Long userId;
    private ReactionType reactionType;
    private ZonedDateTime reactionZoneDateTime;

    @Override
    public boolean validate() {
        boolean isInValid = (postId == null && commentId == null) // Either postId or commentId has to be present
                || reactionType == null; // Reaction type is required
        return !isInValid;
    }

    @Override
    public String getEntityName() {
        return this.getClass().getName();
    }
}
