package com.open.forum.review.application.dto.reaction;

import com.open.forum.review.domain.model.reaction.ReactionType;
import com.open.forum.review.shared.validator.ObjectValidator;
import lombok.*;

@Getter
@Setter
@Builder
public record ReactionCreateDTO(Long postId, Long commentId, Long userId, ReactionType reactionType) implements ObjectValidator {

    /**
     * Validates the object.
     *
     * @return true if the object is valid, false otherwise
     */
    @Override
    public boolean validate() {
        boolean isInvalid = (reactionType == null) // Not a valid reaction type
                || (postId == null && commentId == null) // Either postId (comment) or commentId has to be present
                || (commentId != null && postId != null) // Both postId and commentId cannot be present
                || (userId == null); // User ID is required
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
