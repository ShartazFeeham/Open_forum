package com.open.forum.review.presentation.controllers.comment;

import com.open.forum.review.application.service.ports.CommentServiceReads;
import com.open.forum.review.presentation.models.ErrorResponse;
import com.open.forum.review.presentation.models.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/comments")
    public class CommentReadsController {

        private final CommentServiceReads commentServiceReads;

        public CommentReadsController(CommentServiceReads commentServiceReads) {
            this.commentServiceReads = commentServiceReads;
        }

        @Operation(
                summary = "Read a comment by its ID",
                description = "Fetches a single comment by its unique identifier. The comment must be readable."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Comment fetched successfully"),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error in cases like database exception.",
                        content = @Content(schema = @Schema(example = "An internal server error response"))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad request in case of validation error.",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Comment not found with the given ID.",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                )
        })
        @GetMapping("/{commentId}")
        public ResponseEntity<?> readComment(@NotNull @Positive @PathVariable Long commentId) {
            return ResponseWrapper.result(() -> commentServiceReads.read(commentId), HttpStatus.OK);
        }

        @Operation(
                summary = "Get comments by user ID",
                description = "Fetches a paginated list of comments made by a specific user. The comments must be readable."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Comments fetched successfully"),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error in cases like database exception.",
                        content = @Content(schema = @Schema(example = "An internal server error response"))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad request in case of validation error.",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                )
        })
        @GetMapping("/user/{userId}")
        public ResponseEntity<?> readCommentsByUser(
                @NotNull @Positive @PathVariable Long userId,
                @RequestParam(defaultValue = "0") @Min(0) int page,
                @RequestParam(defaultValue = "10") @Min(1) int size) {
            return ResponseWrapper.result(() -> commentServiceReads.readByUser(userId, page, size), HttpStatus.OK);
        }

        @Operation(
                summary = "Get comments by post ID",
                description = "Fetches a paginated list of comments for a specific post. The comments must be readable."
        )
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "Comments fetched successfully"),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error in cases like database exception.",
                        content = @Content(schema = @Schema(example = "An internal server error response"))
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad request in case of validation error.",
                        content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                )
        })
        @GetMapping("/post/{postId}")
        public ResponseEntity<?> readCommentsByPost(
                @NotNull @Positive @PathVariable Long postId,
                @RequestParam(defaultValue = "0") @Min(0) int page,
                @RequestParam(defaultValue = "10") @Min(1) int size) {
            return ResponseWrapper.result(() -> commentServiceReads.read(postId, page, size), HttpStatus.OK);
        }
    }