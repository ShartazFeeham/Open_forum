package com.open.forum.review.domain.events.comment;

import com.open.forum.review.domain.events.Event;
import com.open.forum.review.domain.model.comment.Comment;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an event that is triggered when a comment is updated.
 * It extends the CommentEvent class and contains the necessary information about the comment.
 *
 * @see CommentEvent
 */
@Getter @Setter
public class CommentUpdatedEvent extends CommentEvent {

    public CommentUpdatedEvent(Comment comment) {
        super(comment.getPostId(),  Event.COMMENT_UPDATED, comment.getUserId(), comment);
    }

}
