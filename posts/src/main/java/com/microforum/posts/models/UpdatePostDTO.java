package com.microforum.posts.models;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder
public class UpdatePostDTO {
    private String title;
    private String content;
    private String category;
    private String subCategory;
    private List<String> tags;
}
