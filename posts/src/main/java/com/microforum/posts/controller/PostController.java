package com.microforum.posts.controller;

import com.microforum.posts.entity.Post;
import com.microforum.posts.models.CreatePostDTO;
import com.microforum.posts.models.UpdatePostDTO;
import com.microforum.posts.service.interfaces.PostService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody @NonNull CreatePostDTO postDTO) {
        Post post = postService.create(postDTO);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable @NonNull Long id) {
        Post post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable @NonNull Long id,
                                           @RequestBody @NonNull UpdatePostDTO postDTO) {
        Post updatedPost = postService.update(id, postDTO);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable @NonNull Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(required = false, defaultValue = "1") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size) {
        List<Post> posts = postService.getAllPost(page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable @NonNull Long userId,
                                                       @RequestParam(required = false, defaultValue = "1") int page,
                                                       @RequestParam(required = false, defaultValue = "10") int size) {
        List<Post> posts = postService.getPostByUserId(userId, page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Post>> getPostsByCategory(@PathVariable @NonNull String category,
                                                         @RequestParam(required = false, defaultValue = "1") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        List<Post> posts = postService.getPostByCategory(category, page, size);
        return ResponseEntity.ok(posts);
    }
}