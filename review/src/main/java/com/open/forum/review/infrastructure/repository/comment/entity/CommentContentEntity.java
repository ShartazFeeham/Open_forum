package com.open.forum.review.infrastructure.repository.comment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @Builder @NoArgsConstructor
public class CommentContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentContentId;
    private Long commentId;
    private String content;
    private ZonedDateTime createdAt;
}
