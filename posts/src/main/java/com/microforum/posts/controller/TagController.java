package com.microforum.posts.controller;

import com.microforum.posts.entity.Tag;
import com.microforum.posts.exception.ErrorResponse;
import com.microforum.posts.service.interfaces.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@io.swagger.v3.oas.annotations.tags.Tag(
        name = "REST API for Tags",
        description = "Get all tags, get tag by name, get tag by id, get tags list by names with pagination."
)
@RestController
@RequestMapping(value = "/tags", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @Operation(
            summary = "Get all tags",
            description = "Get all tags with pagination. " +
                    "Page: the number of the page. Size: how many items to fit in a single page."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of tags is fetched successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request in case of validation error in pagination fields. Example: page = -1",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
    })
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags(@RequestParam(required = false, defaultValue = "1") int page,
                                                @RequestParam(required = false, defaultValue = "10") int size) {
        var result = tagService.getAllTags(page, size);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Get tag by name",
            description = "Provide the name of the tag and fetch the tag along with other details."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The tag is fetched successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request in case of validation error in pagination fields or tag name is null. Example: page = -1",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable @NonNull String name) {
        var result = tagService.getTagByName(name);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Get tag by id",
            description = "Provide the id of the tag and fetch the tag along with other details."
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The tag is fetched successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request in case of validation error in pagination fields or tag id is null.. Example: page = -1",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
    })
    @GetMapping("/id/{id}")
    public ResponseEntity<Tag> getTagByName(@PathVariable @NonNull Long id) {
        var result = tagService.getTagById(id);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Get tags list by names",
            description = "Provide the list of tag names and fetch the tags along with other details."
    )

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of tags is fetched successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request in case of validation error in pagination fields. Example: page = -1",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
    })
    @GetMapping("/names")
    public ResponseEntity<List<Tag>> getTagsListByNames(@RequestParam @NonNull List<String> tags) {
        var result = tagService.getTagsListByNames(tags);
        return ResponseEntity.ok(result);
    }

}
