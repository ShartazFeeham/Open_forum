package com.open.forum.review.infrastructure.repository.reaction.entity;

import com.open.forum.review.domain.model.reaction.ReactionStatus;
import com.open.forum.review.domain.model.reaction.ReactionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class ReactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reactionId;
    private Long postId;
    private Long commentId;
    @Column(nullable = false)
    private Long userId;
    @Enumerated(EnumType.STRING)
    private ReactionStatus reactionStatus;
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
    private ZonedDateTime createdAt;
}