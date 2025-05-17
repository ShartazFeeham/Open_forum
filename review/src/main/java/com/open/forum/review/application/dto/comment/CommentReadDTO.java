package com.open.forum.review.application.dto.comment;

import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.model.comment.CommentContent;
import com.open.forum.review.domain.model.comment.CommentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;

@Builder @Getter @Setter
public class CommentReadDTO {

    private Long commentId;
    private Long postId;
    private Long userId;
    private boolean isReply;
    private Long parentCommentId;
    List<CommentContent> contents;
    private CommentStatus status;
    private ZonedDateTime createdAt;

    public static CommentReadDTO from(Comment comment) {
        return CommentReadDTO.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .createdAt(comment.getCreatedAt())
                .status(comment.getStatus())
                .isReply(comment.isReply())
                .parentCommentId(comment.getParentCommentId())
                .contents(comment.getContents())
                .build();
    }
}
