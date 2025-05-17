package com.open.forum.review.presentation.controllers.comment;

import com.open.forum.review.application.dto.comment.CommentCreateDTO;
import com.open.forum.review.application.dto.comment.CommentUpdateDTO;
import com.open.forum.review.application.service.ports.CommentServiceWrites;
import com.open.forum.review.presentation.models.ErrorResponse;
import com.open.forum.review.presentation.models.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentWritesController {

    private final CommentServiceWrites commentServiceWrites;

    public CommentWritesController(CommentServiceWrites commentServiceWrites) {
        this.commentServiceWrites = commentServiceWrites;
    }

    @Operation(
            summary = "Create a new comment",
            description = "Adds a new comment to a post. The comment must pass validation checks."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comment created successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request in case of validation error.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            )
    })
    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody CommentCreateDTO dto) {
        return ResponseWrapper.result(() -> {
            commentServiceWrites.create(dto);
            return "Comment created successfully";
        }, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing comment",
            description = "Updates the details of an existing comment. The comment must exist and pass validation checks."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment updated successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request in case of validation error.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found with the given ID.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            )
    })
    @PutMapping
    public ResponseEntity<?> updateComment(@Valid @RequestBody CommentUpdateDTO dto) {
        return ResponseWrapper.result(() -> {
            commentServiceWrites.update(dto);
            return "Comment updated successfully";
        }, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a comment by its ID",
            description = "Deletes a comment by its unique identifier. The comment must exist."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found with the given ID.",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            )
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@NotNull @Positive @PathVariable Long commentId) {
        return ResponseWrapper.result(() -> {
            commentServiceWrites.delete(commentId);
            return "Comment deleted successfully";
        }, HttpStatus.OK);
    }
}