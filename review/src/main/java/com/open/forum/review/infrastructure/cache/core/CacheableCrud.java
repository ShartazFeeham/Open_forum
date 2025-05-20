package com.open.forum.review.infrastructure.cache.core;

/**
 * This interface defines a template for cache operations.
 * @param <ID> the type of the identifier for the cached object
 * @param <ENTITY> the type of the cached object
 */
public interface CacheableCrud<ID, ENTITY> {
    ENTITY create(ID id, ENTITY entity);
    ENTITY read(ID id);
    ENTITY update(ID id, ENTITY entity);
    ENTITY delete(ID id);
}
