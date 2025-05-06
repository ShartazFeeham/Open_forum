package com.open.forum.review.application.dto;

import com.open.forum.review.shared.validator.ObjectValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @Builder
public class CommentUpdateDTO implements ObjectValidator {

    private final Long commentId;
    private final String content;

    @Override
    public String getEntityName() {
        return this.getClass().getName();
    }
}
