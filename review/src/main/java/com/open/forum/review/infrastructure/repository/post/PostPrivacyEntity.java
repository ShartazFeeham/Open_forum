package com.open.forum.review.infrastructure.repository.post;

import com.open.forum.review.shared.PostPrivacy;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PostPrivacyEntity {
    @Id
    private Long postId;
    @Enumerated
    private PostPrivacy postPrivacy;
}
