package com.open.forum.review.domain.events;

import lombok.Getter;

/**
 * This enum represents the different events that can occur in the review system.
 * Each event has a title and a description.
 */
@Getter
public enum Event {
    // Comment events
    COMMENT_CREATED ("COMMENT_CREATED", "A new comment had been added."),
    COMMENT_DELETED("COMMENT_DELETED", "A comment has been deleted."),
    COMMENT_UPDATED("COMMENT_UPDATED", "A comment has been updated."),
    COMMENT_REJECTED("COMMENT_REJECTED", "A comment has been rejected."),

    // Reaction events
    REACTION_CREATED("REACTION_CREATED", "A reaction has been created."),
    REACTION_UPDATED("REACTION_UPDATED", "A reaction has been updated."),
    REACTION_DELETED("REACTION_DELETED", "A reaction has been deleted.");

    private final String title;
    private final String description;

    Event(String name, String description) {
        this.title = name;
        this.description = description;
    }
}
