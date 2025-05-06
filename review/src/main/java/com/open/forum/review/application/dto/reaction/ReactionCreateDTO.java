package com.open.forum.review.application.dto.reaction;

import com.open.forum.review.domain.model.reaction.ReactionType;
import lombok.*;

@Getter @Setter @AllArgsConstructor @Builder
public class ReactionCreateDTO {
    private final Long postId;
    private final Long commentId;
    private final Long userId;
    private final ReactionType reactionType;
}
