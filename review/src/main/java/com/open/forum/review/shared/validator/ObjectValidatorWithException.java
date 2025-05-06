package com.open.forum.review.shared.validator;

import com.open.forum.review.shared.exception.InvalidEntityException;

@FunctionalInterface
public interface ObjectValidatorWithException extends ObjectValidator {
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
