package com.open.forum.review.infrastructure.cache;

import com.open.forum.review.domain.cache.UserExistenceCache;
import org.springframework.stereotype.Service;

@Service
public class UserExistenceCacheImpl implements UserExistenceCache {
    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the ID of the user
     * @return true if the user exists, false if it does not exist, null if not checked yet
     */
    @Override
    public Boolean isUserExist(Long userId) {
        return null;
    }

    /**
     * Checks if a user exists by their username.
     *
     * @param username the username of the user
     * @return true if the user exists, false if it does not exist, null if not checked yet
     */
    @Override
    public Boolean isUserExist(String username) {
        return null;
    }
}
