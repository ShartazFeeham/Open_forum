package com.open.forum.review.application.dto.reaction;

import com.open.forum.review.domain.model.reaction.Reaction;
import com.open.forum.review.domain.model.reaction.ReactionType;
import com.open.forum.review.shared.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter @Setter @AllArgsConstructor @Builder
public class ReactionUpdateDTO {

    private final Long reactionId;
    private final ReactionType reactionType;

    private static final Logger log = LoggerFactory.getLogger(ReactionUpdateDTO.class);

    public boolean validate(Long reactionId, Reaction reaction) {
        return reaction != null && reaction.getCommentId() != null
                && reaction.getCommentId().equals(reactionId);
    }

    public void validateOrThrow(Long reactionId, Reaction reaction) {
        if (!validate(reactionId, reaction)) {
            final var exception = new EntityNotFoundException("No record found for " + this.getClass().getName() + " with ID: " + reactionId);
            log.error("Validation failed for {}", this.getClass().getName(), exception);
            throw exception;
        }
    }
}
