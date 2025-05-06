package com.open.forum.review.application.dto.comment;

import com.open.forum.review.domain.model.comment.Comment;

public class CommentReadDTO extends Comment {

    public static CommentReadDTO from(Comment comment) {
        return (CommentReadDTO) comment;
    }
}
