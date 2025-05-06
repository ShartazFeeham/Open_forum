package com.open.forum.review.application.dto;

import com.open.forum.review.domain.model.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CommentReadDTO extends Comment {
}
