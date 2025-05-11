package com.open.forum.review.shared.exception;

import org.springframework.http.HttpStatus;

/**
 * This class represents an exception that is thrown when an entity is invalid.
 * It extends the CustomException class and provides a constructor to set the exception name, message, and HTTP status.
 */
public class InvalidEntityException extends CustomException{
    /**
     * Constructor to create an InvalidEntityException with a specific message.
     *
     * @param message The message to be associated with the exception.
     */
    public InvalidEntityException(String message) {
        super("InvalidEntityException", message, HttpStatus.BAD_REQUEST);
    }
}
