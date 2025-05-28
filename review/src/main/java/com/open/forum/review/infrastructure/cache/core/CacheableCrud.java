package com.open.forum.review.infrastructure.cache.core;

import java.util.Optional;

/**
 * A generic interface for CRUD operations on cacheable entities.
 * @param <ID> the type of the identifier for the cached object
 * @param <ENTITY> the type of the cached object
 */
public interface CacheableCrud<ID, ENTITY> {
    /**
     * Creates a new entity in the cache and source.
     *
     * @param id the identifier of the entity
     * @param entity the entity to create
     * @return the created entity
     */
    ENTITY create(ID id, ENTITY entity);

    /**
     * Reads an entity from the cache or source.
     *
     * @param id the identifier of the entity
     * @return an Optional containing the entity if found, or empty if not found
     */
    Optional<ENTITY> read(ID id);

    /**
     * Updates an existing entity in the cache and source.
     *
     * @param id the identifier of the entity
     * @param entity the entity to update
     * @return the updated entity
     */
    ENTITY update(ID id, ENTITY entity);

    /**
     * Deletes an entity from the cache and source.
     *
     * @param id the identifier of the entity to delete
     * @return the deleted entity
     */
    ENTITY delete(ID id);
}
