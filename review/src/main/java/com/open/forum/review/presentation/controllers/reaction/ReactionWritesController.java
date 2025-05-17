package com.open.forum.review.presentation.controllers.reaction;

import com.open.forum.review.application.dto.reaction.ReactionCreateDTO;
import com.open.forum.review.application.dto.reaction.ReactionUpdateDTO;
import com.open.forum.review.application.service.ports.ReactionServiceWrites;
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
@RequestMapping("/api/v1/reactions")
public class ReactionWritesController {

    private final ReactionServiceWrites reactionServiceWrites;

    public ReactionWritesController(ReactionServiceWrites reactionServiceWrites) {
        this.reactionServiceWrites = reactionServiceWrites;
    }

    @Operation(summary = "Create a new reaction", description = "Adds a new reaction to a post or comment.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reaction created successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request in case of validation error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(example = "An internal server error response")))
    })
    @PostMapping
    public ResponseEntity<?> createReaction(@Valid @RequestBody ReactionCreateDTO dto) {
        return ResponseWrapper.result(() -> {
            reactionServiceWrites.create(dto);
            return "Reaction added successfully";
        }, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing reaction", description = "Updates the details of an existing reaction.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reaction updated successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request in case of validation error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Reaction not found with the given ID.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(example = "An internal server error response")))
    })
    @PutMapping
    public ResponseEntity<?> updateReaction(@Valid @RequestBody ReactionUpdateDTO dto) {
        return ResponseWrapper.result(() -> {
            reactionServiceWrites.update(dto);
            return "Reaction updated successfully";
        }, HttpStatus.OK);
    }

    @Operation(summary = "Delete a reaction by its ID", description = "Deletes a reaction by its unique identifier.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reaction deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Reaction not found with the given ID.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error.", content = @Content(schema = @Schema(example = "An internal server error response")))
    })
    @DeleteMapping("/{reactionId}")
    public ResponseEntity<?> deleteReaction(@NotNull @Positive @PathVariable Long reactionId) {
        return ResponseWrapper.result(() -> {
            reactionServiceWrites.deleteReaction(reactionId);
            return "Reaction deleted successfully";
        }, HttpStatus.OK);
    }
}