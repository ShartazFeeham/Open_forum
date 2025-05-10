package com.open.forum.review.application.dto.reaction;

import com.open.forum.review.domain.model.reaction.ReactionType;
import lombok.*;

@Getter
@Setter
@Builder
public record ReactionCreateDTO(Long postId, Long commentId, Long userId, ReactionType reactionType) {
}
