package com.microforum.posts.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
@Schema(description = "Tag entity, used to store tags of a post", name = "Tag")
public class Tag extends BaseEntity {
    @Schema(description = "Name of the tag", example = "technology")
    @NotEmpty(message = "Name of the tag cannot be empty")
    private String name;
}
