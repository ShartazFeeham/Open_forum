package com.open.forum.review.domain.events.comment;

import com.open.forum.review.domain.events.Event;
import com.open.forum.review.domain.model.comment.Comment;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an event that is triggered when a comment is created.
 * It extends the CommentEvent class and contains the necessary information about the comment.
 *
 * @see CommentEvent
 */
@Getter @Setter
public class CommentCreatedEvent extends CommentEvent {

    public CommentCreatedEvent(Comment comment) {
        super(comment.getPostId(),  Event.COMMENT_CREATED, comment.getUserId(), comment);
    }

}
