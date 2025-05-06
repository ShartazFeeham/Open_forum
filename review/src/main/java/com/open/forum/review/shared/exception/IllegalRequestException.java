package com.open.forum.review.shared.exception;

import org.springframework.http.HttpStatus;

public class IllegalRequestException extends CustomException {
    public IllegalRequestException(String message) {
        super("IllegalRequestException", message, HttpStatus.NOT_ACCEPTABLE);
    }
}
