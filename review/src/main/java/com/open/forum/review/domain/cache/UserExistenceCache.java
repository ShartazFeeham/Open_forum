package com.open.forum.review.domain.cache;

import jakarta.validation.constraints.NotNull;

public interface UserExistenceCache {
    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the ID of the user
     * @return true if the user exists, false if it does not exist, null if not checked yet
     */
    Boolean isUserExist(@NotNull Long userId);

    /**
     * Checks if a user exists by their username.
     *
     * @param username the username of the user
     * @return true if the user exists, false if it does not exist, null if not checked yet
     */
    Boolean isUserExist(@NotNull String username);
}
