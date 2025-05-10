package com.open.forum.review.domain.cache;

public interface UserExistenceCache {
    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the ID of the user
     * @return true if the user exists, false if it does not exist, null if not checked yet
     */
    Boolean isUserExist(Long userId);

    /**
     * Checks if a user exists by their username.
     *
     * @param username the username of the user
     * @return true if the user exists, false if it does not exist, null if not checked yet
     */
    Boolean isUserExist(String username);
}
