package com.open.forum.review.presentation.controllers.reaction;

import com.open.forum.review.application.service.ports.ReactionServiceReads;
import com.open.forum.review.presentation.models.ErrorResponse;
import com.open.forum.review.presentation.models.ResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reactions")
public class ReactionReadsController {

    private final ReactionServiceReads reactionServiceReads;

    public ReactionReadsController(ReactionServiceReads reactionServiceReads) {
        this.reactionServiceReads = reactionServiceReads;
    }

    @Operation(summary = "Read a reaction by its ID", description = "Fetches a reaction by its unique identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reaction fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Reaction not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(example = "An internal server error response")))
    })
    @GetMapping("/{reactionId}")
    public ResponseEntity<?> readReaction(@NotNull @Positive @PathVariable Long reactionId) {
        return ResponseWrapper.result(() -> reactionServiceReads.readById(reactionId), HttpStatus.OK);
    }

    @Operation(summary = "Read reactions by post ID", description = "Fetches all reactions for a specific post.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reactions fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(example = "An internal server error response")))
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> readReactionsByPost(@NotNull @Positive @PathVariable Long postId) {
        return ResponseWrapper.result(() -> reactionServiceReads.readReactionsByPostId(postId, 0, 10), HttpStatus.OK);
    }

    @Operation(summary = "Read reaction count by post ID", description = "Fetches the count of reactions for a specific post.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reaction count fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(example = "An internal server error response")))
    })
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<?> readReactionsCountByPost(@NotNull @Positive @PathVariable Long postId) {
        return ResponseWrapper.result(() -> reactionServiceReads.readReactionsCountByPostId(postId), HttpStatus.OK);
    }

    @Operation(summary = "Read reactions by comment ID", description = "Fetches all reactions for a specific comment.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reactions fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(example = "An internal server error response")))
    })
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<?> readReactionsByComment(@NotNull @Positive @PathVariable Long commentId) {
        return ResponseWrapper.result(() -> reactionServiceReads.readReactionsByCommentId(commentId), HttpStatus.OK);
    }

    @Operation(summary = "Read reaction count by comment ID", description = "Fetches the count of reactions for a specific comment.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reaction count fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(example = "An internal server error response")))
    })
    @GetMapping("/comment/{commentId}/count")
    public ResponseEntity<?> readReactionsCountByComment(@NotNull @Positive @PathVariable Long commentId) {
        return ResponseWrapper.result(() -> reactionServiceReads.readReactionsCountByCommentId(commentId), HttpStatus.OK);
    }
}