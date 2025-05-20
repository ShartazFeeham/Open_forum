package com.open.forum.review.infrastructure.cache.core;

import jakarta.validation.constraints.NotNull;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Optional;

/**
 * This interface defines a template for cache operations.
 * @param <ID> the type of the identifier for the cached object
 * @param <VALUE> the type of the cached object
 */
public abstract class CrudCacheTemplate<ID, VALUE> implements CacheableCrud<ID, VALUE> {

    private static final Logger log = LoggerFactory.getLogger(CrudCacheTemplate.class);
    protected final RedissonClient redissonClient;

    protected CrudCacheTemplate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    abstract protected @NotNull String cacheKey(ID id);
    abstract protected Optional<VALUE> getFromSource(ID key);
    abstract protected VALUE createToSource(VALUE value);
    abstract protected VALUE updateToSource(ID key, VALUE value);
    abstract protected VALUE deleteToSource(ID key);
    abstract protected @NotNull Long expirationTimeInSeconds();

    // It works with a lazy loading strategy, so it will not add an item in the cache while creating it in the source
    @Override
    public VALUE create(@NotNull ID id, VALUE value) {
        final String cacheKey = cacheKey(id);
        log.info("Key {} exists in source, creating an item", id);
        final VALUE newItem = createToSource(value);
        log.info("Item created: {}", newItem);
        return newItem;
    }

    @Override
    public Optional<VALUE> read(ID id) {
        final String cacheKey = cacheKey(id);
        log.info("Cache key: {}", cacheKey);
        final RBucket<VALUE> bucket = redissonClient.getBucket(cacheKey);
        if (bucket.isExists()) {
            log.info("Cache hit for key {}: {}", cacheKey, bucket.get());
            return Optional.of(bucket.get());
        }
        log.info("Cache miss for key {}: fetching from source", cacheKey);
        final Optional<VALUE> value = getFromSource(id);
        if (value.isEmpty()) {
            log.info("Item not found in source");
            return Optional.empty();
        }
        log.info("Item fetched from source: {}", value);
        bucket.set(value.get());
        bucket.expire(Duration.ofSeconds(expirationTimeInSeconds()));
        log.info("Storing the item in the cache with key {}: {}, with expiration time: {}",
                cacheKey, value, expirationTimeInSeconds());
        return Optional.empty();
    }

    @Override
    public VALUE update(ID id, VALUE value) {
        final String cacheKey = cacheKey(id);
        log.info("Cache key: {}", cacheKey);
        final RBucket<VALUE> bucket = redissonClient.getBucket(cacheKey);
        final VALUE updatedItem = updateToSource(id, value);
        log.info("Item updated: {}", updatedItem);
        bucket.set(updatedItem);
        log.info("Item cached with key {}: {}", cacheKey, updatedItem);
        return updatedItem;
    }

    @Override
    public VALUE delete(ID id) {
        final String cacheKey = cacheKey(id);
        log.info("Cache key: {}", cacheKey);
        final RBucket<VALUE> bucket = redissonClient.getBucket(cacheKey);
        final VALUE deletedItem = deleteToSource(id);
        log.info("Item deleted: {}", deletedItem);
        bucket.delete();
        log.info("Item deleted from cache with key {}", cacheKey);
        return deletedItem;
    }
}
