package com.open.forum.review.application.mapper;

import com.open.forum.review.application.dto.comment.CommentCreateDTO;
import com.open.forum.review.application.dto.comment.CommentReadDTO;
import com.open.forum.review.application.dto.comment.CommentUpdateDTO;
import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.model.comment.CommentContent;
import com.open.forum.review.domain.model.comment.CommentStatus;
import com.open.forum.review.shared.utility.ServerZonedDateTime;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Mapper class for converting between Comment-related DTOs and the Comment entity.
 * This class provides methods to convert CommentCreateDTO, CommentUpdateDTO, and CommentReadDTO to and from the Comment entity.
 */
public class CommentMapper {

    /**
     * Converts a CommentCreateDTO to a Comment entity.
     *
     * @param dto the CommentCreateDTO to convert
     * @return the converted Comment entity
     */
    public static Comment toComment(CommentCreateDTO dto) {
        final var builder = Comment.builder()
                .commentId(dto.commentId())
                .status(CommentStatus.PENDING)
                .createdAt(ServerZonedDateTime.now())
                .userId(dto.userId())
                .isReply(dto.isReply())
                .contents(new ArrayList<>(List.of(CommentContent.builder().content(dto.content()).build())));
        if (dto.isReply()) {
            builder.parentCommentId(dto.parentCommentId());
        } else {
            builder.postId(dto.postId());
        }
        return builder.build();
    }

    /**
     * Converts a CommentUpdateDTO to a Comment entity.
     *
     * @param dto the CommentUpdateDTO to convert
     * @param comment the existing Comment entity to update
     * @return the updated Comment entity
     */
    public static Comment toComment(CommentUpdateDTO dto, Comment comment) {
        comment.getContents().add(CommentContent.builder()
                .content(dto.content())
                .commentId(dto.commentId())
                .createdAt(ServerZonedDateTime.now())
                .build());
        return comment;
    }

    /**
     * Converts a Comment entity to a CommentReadDTO.
     *
     * @param comment the Comment entity to convert
     * @return the converted CommentReadDTO
     */
    public static CommentReadDTO toCommentReadDTO(Comment comment) {
        return CommentReadDTO.from(comment);
    }

}
