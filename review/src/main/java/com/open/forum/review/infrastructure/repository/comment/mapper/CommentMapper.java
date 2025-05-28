package com.open.forum.review.infrastructure.repository.comment.mapper;

import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.model.comment.CommentContent;
import com.open.forum.review.infrastructure.repository.comment.entity.CommentContentEntity;
import com.open.forum.review.infrastructure.repository.comment.entity.CommentEntity;

import java.util.stream.Collectors;

public class CommentMapper {

    public static Comment toComment(CommentEntity commentEntity) {
        if (commentEntity == null) {
            return null;
        }

        return Comment.builder()
                .commentId(commentEntity.getCommentId())
                .postId(commentEntity.getPostId())
                .userId(commentEntity.getUserId())
                .isReply(commentEntity.isReply())
                .parentCommentId(commentEntity.getParentCommentId())
                .contents(commentEntity.getContents().stream()
                        .map(contentEntity -> CommentContent.builder()
                                .commentId(contentEntity.getCommentId())
                                .content(contentEntity.getContent())
                                .createdAt(contentEntity.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .status(commentEntity.getStatus())
                .createdAt(commentEntity.getCreatedAt())
                .build();
    }

    public static CommentEntity toCommentEntity(Comment comment) {
        if (comment == null) {
            return null;
        }

        return CommentEntity.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPostId())
                .userId(comment.getUserId())
                .isReply(comment.isReply())
                .parentCommentId(comment.getParentCommentId())
                .contents(comment.getContents().stream()
                        .map(content -> CommentContentEntity.builder()
                                .commentId(content.getCommentId())
                                .content(content.getContent())
                                .createdAt(content.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .status(comment.getStatus())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}