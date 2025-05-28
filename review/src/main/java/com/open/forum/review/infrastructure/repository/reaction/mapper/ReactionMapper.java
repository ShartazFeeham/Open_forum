package com.open.forum.review.infrastructure.repository.reaction.mapper;

import com.open.forum.review.domain.model.reaction.Reaction;
import com.open.forum.review.infrastructure.repository.reaction.entity.ReactionEntity;

public class ReactionMapper {

    public static Reaction toReaction(ReactionEntity reactionEntity) {
        if (reactionEntity == null) {
            return null;
        }

        return Reaction.builder()
                .reactionId(reactionEntity.getReactionId())
                .postId(reactionEntity.getPostId())
                .commentId(reactionEntity.getCommentId())
                .userId(reactionEntity.getUserId())
                .reactionStatus(reactionEntity.getReactionStatus())
                .reactionType(reactionEntity.getReactionType())
                .createdAt(reactionEntity.getCreatedAt())
                .build();
    }

    public static ReactionEntity toReactionEntity(Reaction reaction) {
        if (reaction == null) {
            return null;
        }

        return ReactionEntity.builder()
                .reactionId(reaction.getReactionId())
                .postId(reaction.getPostId())
                .commentId(reaction.getCommentId())
                .userId(reaction.getUserId())
                .reactionStatus(reaction.getReactionStatus())
                .reactionType(reaction.getReactionType())
                .createdAt(reaction.getCreatedAt())
                .build();
    }
}