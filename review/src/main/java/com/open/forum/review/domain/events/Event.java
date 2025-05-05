package com.open.forum.review.domain.events;

import lombok.Getter;

/**
 * This enum represents the different events that can occur in the review system.
 * Each event has a title and a description.
 */
@Getter
public enum Event {

    COMMENT_CREATED ("COMMENT_CREATED", "A new comment had been added."),
    COMMENT_DELETED("COMMENT_DELETED", "A comment has been deleted."),
    COMMENT_UPDATED("COMMENT_UPDATED", "A comment has been updated."),
    REACTION_REQUESTED("REACTION_REQUESTED", "A reaction has been requested."),
    REACTION_APPLIED("REACTION_APPLIED", "A reaction has been applied.");

    private final String title;
    private final String description;

    Event(String name, String description) {
        this.title = name;
        this.description = description;
    }
}
