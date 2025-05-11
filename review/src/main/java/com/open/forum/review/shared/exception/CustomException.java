package com.open.forum.review.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter @AllArgsConstructor
public class CustomException extends RuntimeException {
    private final String exceptionName;
    private final String message;
    private final HttpStatus httpStatus;
}