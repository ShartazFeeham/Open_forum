package com.microforum.posts.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Post entity, used to store post data", name = "Post")
public class Post extends BaseEntity {
    @Schema(description = "Id of the user who created the post", example = "1")
    private Long userId;

    @Schema(description = "Username user who created the post", example = "JohnDoeX")
    private String userName;

    @Schema(description = "Full name of the user who created the post", example = "John Doe")
    private String userFullName;

    @Schema(description = "Title of the post", example = "Top 10 places to explore in Island")
    private String title;

    @Schema(description = "Content of the post", example = """
        Exploring the Island was the best trip of my life.\
        I had a chance to visit the beaches, the mountains, the cities and the villages.\
        I met the locals and they were...""")
    private String content;

    @Schema(description = "Category of the post", example = "Travel")
    private String category;

    @Schema(description = "Subcategory of the post", example = "Adventure")
    private String subCategory;

    @Schema(description = "Tags of the post", example = "Adventure, Travel, Island")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private List<Tag> tags;

    @Schema(description = "Age restriction of the post", example = "true")
    private boolean ageFlag;

    @Schema(description = "Status of the post", example = "PUBLISHED")
    private Status status;

    @Schema(description = "Review/Reaction count of the post", example = "0")
    private Long reviewCount;
}
