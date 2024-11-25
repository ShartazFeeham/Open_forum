package com.microforum.posts.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter @Setter @AllArgsConstructor @Builder
public class ErrorResponse {
    private String exception;
    private String message;
    private String status;
    private Date timeStamp;
    private String requestURI;
    private String requestURL;
}