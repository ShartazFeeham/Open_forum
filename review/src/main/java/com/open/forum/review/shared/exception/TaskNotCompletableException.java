package com.open.forum.review.shared.exception;

import org.springframework.http.HttpStatus;

public class TaskNotCompletableException extends CustomException {
    public TaskNotCompletableException(String message) {
        super("TaskNotCompletableException", message, HttpStatus.NOT_ACCEPTABLE);
    }
}
