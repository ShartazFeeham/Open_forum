package com.microforum.posts.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter @Setter @AllArgsConstructor @Builder
@Schema(
    name = "ErrorResponse",
    description = "Schema to hold error response information"
)
public class ErrorResponse {
    @Schema(description = "Exception name", example = "ResourceNotFoundException")
    private String exception;

    @Schema(description = "Exception message", example = "User with userId: 1 not found")
    private String message;

    @Schema(description = "Exception status", example = "NOT_FOUND")
    private String status;

    @Schema(description = "Exception timestamp", example = "2021-01-01T00:00:00")
    private Date timeStamp;

    @Schema(description = "Exception request URI", example = "/users/1")
    private String requestURI;

    @Schema(description = "Exception request URL", example = "http://localhost:8080/users/1")
    private String requestURL;
}