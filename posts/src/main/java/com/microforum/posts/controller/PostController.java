package com.microforum.posts.controller;

import com.microforum.posts.entity.Post;
import com.microforum.posts.exception.ErrorResponse;
import com.microforum.posts.models.CreatePostDTO;
import com.microforum.posts.models.UpdatePostDTO;
import com.microforum.posts.service.interfaces.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST API for Posts",
        description = "Create, read, update and delete posts, get all posts or by user or by category with pagination."
)
@RestController
@RequestMapping(value = "/posts", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(
            summary = "Create a new post",
            description = "Create a new post with the provided data."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Post created successfully"),
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
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody @NonNull CreatePostDTO postDTO) {
        Post post = postService.create(postDTO);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(post);
    }

    @Operation(
            summary = "Get a post by its id",
            description = "Provide a post id to get the corresponding post."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post is fetched successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "If item is not found, 404 error is thrown with ResourceNotFound exception",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable @NonNull Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(post);
    }

    @Operation(
        summary = "Update a post by its id",
        description = "Select a post by its ID and update the post with the provided data."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post is updated successfully"),
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
                    description = "If item is not found, 404 error is thrown with ResourceNotFound exception",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable @NonNull Long id,
                                           @RequestBody @NonNull UpdatePostDTO postDTO) {
        Post updatedPost = postService.update(id, postDTO);
        return ResponseEntity.status(HttpStatus.OK.value()).body(updatedPost);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Post is deleted successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "If item is not found, 404 error is thrown with ResourceNotFound exception",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @Operation(
            summary = "Delete a post by its id",
            description = "Delete the post with the provided id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable @NonNull Long id) {
        postService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @Operation(
            summary = "Get all posts",
            description = "Get all posts with pagination. " +
                    "Page: the number of the page. Size: how many items to fit in a single page."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of posts is fetched successfully"),
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
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size) {
        List<Post> posts = postService.getAllPost(page, size);
        return ResponseEntity.ok(posts);
    }

    @Operation(
            summary = "Get all posts by a user",
            description = "Get all posts by a user with pagination. " +
                    "Page: the number of the page. Size: how many items to fit in a single page."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of posts is fetched successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request in case of validation error in pagination fields or userId = null. Example: page = -1",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable @NonNull Long userId,
                                                       @RequestParam(required = false, defaultValue = "1") int page,
                                                       @RequestParam(required = false, defaultValue = "10") int size) {
        List<Post> posts = postService.getPostByUserId(userId, page, size);
        return ResponseEntity.ok(posts);
    }

    @Operation(
            summary = "Get all posts by a category",
            description = "Get all posts by a category with pagination. " +
                    "Page: the number of the page. Size: how many items to fit in a single page."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of posts is fetched successfully"),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error in cases like database exception.",
                    content = @Content(schema = @Schema(example = "An internal server error response"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request in case of validation error in pagination fields or category = null. Example: page = -1",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable @NonNull String category,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        List<Post> posts = postService.getPostByCategory(category, page, size);
        return ResponseEntity.ok(posts);
    }
}