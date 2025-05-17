package com.open.forum.review.presentation.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter @Setter
public class BaseResponseModel<R> {
    private R data;
    private HttpStatus status;
    private ZonedDateTime requestedAt;
    private ZonedDateTime respondedAt;
    private long responseTimeInMillis;
    private String path;
    private String traceId;
}
