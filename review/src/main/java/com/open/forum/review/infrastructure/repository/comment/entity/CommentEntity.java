package com.open.forum.review.infrastructure.repository.comment.entity;

import com.open.forum.review.domain.model.comment.CommentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private Long postId;
    @NotNull @Positive
    private Long userId;
    private boolean isReply;
    private Long parentCommentId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CommentContentEntity> contents;
    private CommentStatus status;
    private ZonedDateTime createdAt;
}
