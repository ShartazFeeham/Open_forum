package com.open.forum.review.domain.model.reaction;

/**
 * Represents the type of reaction a user can have on a post or comment.
 * Each reaction type is associated with a unique integer value.
 */
public enum ReactionType {
    LIKE(1),
    DISLIKE(2),
    HEART(3),
    ANGRY(4),
    SAD(5),
    SUPPORT(6),
    WOW(7),
    HAHA(8),
    CLAP(9),
    DISAPPOINTED(10);

    private final int value;

    ReactionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
