package com.open.forum.review.domain.events.comment;

import com.open.forum.review.domain.events.BaseEvent;
import com.open.forum.review.domain.events.Event;
import com.open.forum.review.domain.model.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a comment event in the system.
 * It extends the BaseEvent class and provides additional information specific to comment events.
 * The COMMENT_EVENT constant is used to identify this type of event.
 */
@Getter @Setter @Builder
class CommentEvent extends BaseEvent<Comment> {

    private static final String COMMENT_EVENT = "COMMENT_EVENT";

    public CommentEvent(Long postId, Event event, Long userId, Comment eventData) {
        super(postId, userId, event, eventData, COMMENT_EVENT);
    }
}
