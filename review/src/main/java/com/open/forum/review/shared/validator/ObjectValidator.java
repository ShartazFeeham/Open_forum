package com.open.forum.review.shared.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Predicate;

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
    default boolean validate() {
        return true;
    }

    /**
     * Returns the name of the entity being validated.
     *
     * @return the name of the entity
     */
    String getEntityName();
}
