package com.open.forum.review.shared.validator;

import com.open.forum.review.shared.exception.InvalidEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interface defines a contract for validating objects.
 * It provides a method to validate the object and a default method to validate and throw an exception if validation fails.
 */
public interface ObjectValidator {

    Logger logger = LoggerFactory.getLogger(ObjectValidator.class);
    /**
     * Validates the object.
     *
     * @return true if the object is valid, false otherwise
     */
    boolean validate();

    /**
     * Returns the name of the entity being validated.
     *
     * @return the name of the entity
     */
    String getEntityName();

    /**
     * Validates the object and throws an exception if validation fails.
     */
    default void validateOrThrow() {
        if (!validate()) {
            final var exception = new InvalidEntityException("The object of " + getEntityName() + " is invalid.");
            logger.error("Validation failed for {}", getEntityName(), exception);
            throw exception;
        }
    }
}
