package com.microforum.posts.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Schema(
    name = "New post",
    description = "Schema to store new post data"
)
public class CreatePostDTO {
    @NotEmpty(message = "Title cannot be empty")
    @Schema(description = "Title of the post", example = "Top 10 places to explore in Island")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotEmpty(message = "Content cannot be empty")
    @Schema(description = "Content of the post", example = """
            Exploring the Island was the best trip of my life.\s
            I had a chance to visit the beaches, the mountains, the cities and the villages.\s
            I met the locals and they were...""")
    @Size(min = 10, max = 30000, message = "Content must be between 10 and 30000 characters")
    private String content;

    @NotEmpty(message = "Category cannot be empty")
    @Schema(description = "Category of the post", example = "Travel")
    private String category;

    @Schema(description = "Subcategory of the post", example = "Adventure")
    private String subCategory;

    @NotEmpty(message = "Tags cannot be empty")
    @Schema(description = "Tags of the post", example = "[Adventure, Travel, Island]")
    @Size(min = 1, max = 5, message = "At least one tag is required, and no more than 5 tags are allowed")
    private List<String> tags;
}
