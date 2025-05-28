package com.open.forum.review.infrastructure.cache;

import com.open.forum.review.domain.cache.UserExistenceCache;
import com.open.forum.review.infrastructure.cache.core.SingleReadCacheTemplate;
import com.open.forum.review.shared.helper.TokenExtractor;
import jakarta.validation.constraints.NotNull;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
public class UserExistenceCacheImpl extends SingleReadCacheTemplate<Long, Boolean> implements UserExistenceCache {

    protected UserExistenceCacheImpl(RedissonClient redissonClient) {
        super(redissonClient);
    }

    /**
     * Checks if a user exists by their ID.
     *
     * @param userId the ID of the user
     * @return true if the user exists, false if it does not exist, null if not checked yet
     */
    @Override
    public Boolean isUserExist(@NotNull Long userId) {
        return TokenExtractor.matchUserId(userId);
    }

    /**
     * Checks if a user exists by their username.
     *
     * @param username the username of the user
     * @return true if the user exists, false if it does not exist, null if not checked yet
     */
    @Override
    public Boolean isUserExist(@NotNull String username) {
        return TokenExtractor.matchUserId(TokenExtractor.extractUserId(username));
    }

    @Override
    protected String cacheKey(Long userId) {
        return "user_existence:" + userId;
    }

    @Override
    protected Boolean getFromSource(Long userId) {
        return isUserExist(userId);
    }

    @Override
    protected Long expirationTimeInSeconds() {
        return 300L; // Cache expiration time is set to 5 minutes (300 seconds)
    }
}
