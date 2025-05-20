package com.open.forum.review.infrastructure.cache.core;

import jakarta.validation.constraints.NotNull;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * This interface defines a template for cache operations.
 * @param <ID> the type of the identifier for the cached object
 * @param <VALUE> the type of the cached object
 */
public abstract class SingleReadCacheTemplate<ID, VALUE> {

    private static final Logger log = LoggerFactory.getLogger(SingleReadCacheTemplate.class);
    protected final RedissonClient redissonClient;

    protected SingleReadCacheTemplate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    abstract protected @NotNull String cacheKey(ID id);
    abstract protected VALUE getFromSource(ID key);
    abstract protected @NotNull Long expirationTimeInSeconds();

    public VALUE read(ID id) {
        final String cacheKey = cacheKey(id);
        log.info("Cache key: {}", cacheKey);
        final RBucket<VALUE> bucket = redissonClient.getBucket(cacheKey);
        if (bucket.isExists()) {
            log.info("Cache hit for key {}: {}", cacheKey, bucket.get());
            return bucket.get();
        }
        log.info("Cache miss for key {}: fetching from source", cacheKey);
        final VALUE value = getFromSource(id);
        log.info("Item fetched from source: {}", value);
        bucket.set(value);
        bucket.expire(Duration.ofSeconds(expirationTimeInSeconds()));
        log.info("Storing the item in the cache with key {}: {}, with expiration time: {}",
                cacheKey, value, expirationTimeInSeconds());
        return value;
    }
}
