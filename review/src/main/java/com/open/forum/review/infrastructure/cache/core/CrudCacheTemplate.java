package com.open.forum.review.infrastructure.cache.core;

import jakarta.validation.constraints.NotNull;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Optional;

/**
 * This interface defines a template for cache operations. It provides methods for creating, reading, updating,
 * and deleting cached objects. Any class that extends this template must implement the methods to related to
 * the source of the cached objects. For simple read operations, consider using {@link SingleReadCacheTemplate} instead.
 * @param <ID> the type of the identifier for the cached object
 * @param <VALUE> the type of the cached object
 */
public abstract class CrudCacheTemplate<ID, VALUE> implements CacheableCrud<ID, VALUE> {

    /**
     * Generates a cache key based on the identifier.
     * @param id the identifier of the cached object
     * @return the cache key as a String
     */
    abstract protected @NotNull String cacheKey(ID id);

    /**
     * Retrieves an object from the source based on the identifier.
     * @param key the identifier of the cached object
     * @return an Optional containing the cached object if found, or empty if not found
     */
    abstract protected Optional<VALUE> getFromSource(ID key);

    /**
     * Creates a new object in the source based on the provided value.
     * @param value the value to create in the source
     * @return the created object
     */
    abstract protected VALUE createToSource(VALUE value);

    /**
     * Updates an existing object in the source based on the provided identifier and value.
     * @param key the identifier of the cached object
     * @param value the value to update in the source
     * @return the updated object
     */
    abstract protected VALUE updateToSource(ID key, VALUE value);

    /**
     * Deletes an object from the source based on the provided identifier.
     * @param key the identifier of the cached object
     * @return the deleted object
     */
    abstract protected VALUE deleteToSource(ID key);

    /**
     * Returns the expiration time for cached objects in seconds.
     * @return the expiration time in seconds
     */
    abstract protected @NotNull Long expirationTimeInSeconds();

    private static final Logger log = LoggerFactory.getLogger(CrudCacheTemplate.class);
    protected final RedissonClient redissonClient;

    /**
     * Constructor for the CrudCacheTemplate.
     * @param redissonClient the Redisson client used for cache operations
     */
    protected CrudCacheTemplate(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

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
