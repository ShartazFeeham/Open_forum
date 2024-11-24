package com.microforum.posts.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class Tag extends BaseEntity {
    private String name;
}
