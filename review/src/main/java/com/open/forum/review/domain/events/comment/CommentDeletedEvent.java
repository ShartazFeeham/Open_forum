package com.open.forum.review.domain.events.comment;

import com.open.forum.review.domain.events.Event;
import com.open.forum.review.domain.model.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an event that is triggered when a comment is deleted.
 * It extends the CommentEvent class and contains the necessary information about the comment.
 *
 * @see CommentEvent
 */
@Getter @Setter @Builder
public class CommentDeletedEvent extends CommentEvent {

    public CommentDeletedEvent(Comment comment) {
        super(comment.getPostId(),  Event.COMMENT_DELETED, comment.getUserId(), comment);
    }

}
