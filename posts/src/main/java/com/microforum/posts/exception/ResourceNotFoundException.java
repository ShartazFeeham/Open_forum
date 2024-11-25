package com.microforum.posts.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException{
    public ResourceNotFoundException(String resourceName, String identifierWithPrefix) {
        super(resourceName +
              "NotFound", "Resource " + resourceName + " not found " + identifierWithPrefix,
              HttpStatus.NOT_FOUND);
    }
}
