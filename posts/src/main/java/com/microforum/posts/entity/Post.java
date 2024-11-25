package com.microforum.posts.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@Table(
    indexes = {
        @Index(name = "posts_id_index", columnList = "id"),
        @Index(name = "posts_user_id_index", columnList = "user_id"),
        @Index(name = "posts_category_index", columnList = "category"),
    }
)
public class Post extends BaseEntity {
    private Long userId;
    private String userName;
    private String userFullName;
    private String title;
    private String content;
    private String category;
    private String subCategory;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<Tag> tags;
    private boolean ageFlag;
    private Status status;
    private Long reviewCount;
}
