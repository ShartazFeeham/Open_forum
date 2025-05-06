package com.open.forum.review.shared.validator;

import java.util.function.Predicate;

@FunctionalInterface
public interface ObjectValidatorWithPredicate <T> extends ObjectValidator {

    /**
     * Validates the object using a custom predicate.
     *
     * @param predicate the predicate to validate the object
     * @return true if the object is valid according to the predicate, false otherwise
     */
    default boolean validate(Predicate<T> predicate) {
        if (predicate == null) {
            throw new IllegalArgumentException("Predicate cannot be null");
        }
        return predicate.test(this);
    }
}
