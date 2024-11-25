package com.microforum.posts.service;

import com.microforum.posts.entity.Post;
import com.microforum.posts.entity.Status;
import com.microforum.posts.exception.ResourceNotFoundException;
import com.microforum.posts.models.CreatePostDTO;
import com.microforum.posts.models.UpdatePostDTO;
import com.microforum.posts.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TagService tagService;
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Transactional
    public Post create(CreatePostDTO postDTO) {
        Long userId = getUserIdFromToken();
        Post post = Post.builder()
                .userId(userId)
                .userName(getUserNameFromToken(userId))
                .userFullName(getUserFullNameFromUserService(userId))
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .category(postDTO.getCategory())
                .subCategory(postDTO.getSubCategory())
                .tags(tagService.getTagsListByIds(postDTO.getTags()))
                .ageFlag(setAgeFlagByAnalyzerService())
                .reviewCount(0L)
                .status(setStatus())
            .build();
        logger.info("Saving post: {}", post);
        var result = postRepository.save(post);
        logger.info("Saved post: {}", result);
        return postRepository.save(post);
    }

    Random random = new Random();
    private Long getUserIdFromToken() {
        // TODO: implement using spring security
        return random.nextLong(5);
    }

    private String getUserNameFromToken(Long userId) {
        // TODO: call user service through message queue
        return "Username " + userId;
    }

    private String getUserFullNameFromUserService(Long userId) {
        // TODO: call user service through message queue
        return "first_name last_name " + userId;
    }

    private boolean setAgeFlagByAnalyzerService() {
        // TODO: call analyzer service through message queue
        return false;
    }

    private Status setStatus() {
        // TODO: aggregate user service and analyzer service response through message queue then set status to active
        return Status.ACTIVE;
    }

    public Post getPostById(Long id) {
        // TODO: throw custom exception
        return postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", " where postId = " + id));
    }

    @Transactional
    public Post update(Long postId, UpdatePostDTO postDTO) {
        validateUserRight();
        Post post = getPostById(postId);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCategory(postDTO.getCategory());
        post.setSubCategory(postDTO.getSubCategory());
        post.setTags(tagService.getTagsListByIds(postDTO.getTags()));
        logger.info("Updating post: {}", post);
        var result = postRepository.save(post);
        logger.info("Updated post: {}", result);
        return postRepository.save(post);
    }

    public void delete(Long id) {
        validateUserRight();
        callReviewServiceToDeleteReview(id);
        Post post = getPostById(id);
        post.setStatus(Status.DELETED);
        logger.info("Deleting post: {}", post);
        var result = postRepository.save(post);
        logger.info("Deleted post: {}", result);
    }

    private void validateUserRight() {
        // TODO: implement
    }

    private void callReviewServiceToDeleteReview(Long id) {
        // TODO: implement
    }

    public List<Post> getPostByUserId(Long userId) {
        var result = postRepository.findByUserId(userId);
        logger.info("Getting post by user id: {} result: {}", userId, result);
        result = result.stream()
                .filter(this::filterPosts)
                .toList();
        logger.info("Filtered posts by user id: {} result: {}", userId, result);
        return result;
    }

    public List<Post> getAllPost() {
        var result = postRepository.findAll();
        logger.info("Getting all posts, result: {}", result);
        result = result.stream()
                .filter(this::filterPosts)
                .toList();
        logger.info("Filtered all posts, result: {}", result);
        return result;
    }

    private boolean filterPosts(Post post) {
        // TODO: Filter by status, age flag, own post
        return true;
    }
}
