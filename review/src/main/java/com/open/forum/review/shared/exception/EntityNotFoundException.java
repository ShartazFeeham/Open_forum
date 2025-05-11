package com.open.forum.review.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * This class represents an exception that is thrown when an entity is not found.
 * It extends the CustomException class and provides a constructor to set the exception name, message, and HTTP status.
 */
public class EntityNotFoundException extends CustomException{
    /**
     * Constructor to create an EntityNotFoundException with a specific message.
     *
     * @param message The message to be associated with the exception.
     */
    public EntityNotFoundException(String message) {
        super("EntityNotFoundException", message, HttpStatus.NOT_FOUND);
    }
}
