package com.microforum.posts.service;

import com.microforum.posts.entity.Post;
import com.microforum.posts.entity.Status;
import com.microforum.posts.exception.ResourceNotFoundException;
import com.microforum.posts.models.CreatePostDTO;
import com.microforum.posts.models.DynamicModel;
import com.microforum.posts.models.UpdatePostDTO;
import com.microforum.posts.repository.PostRepository;
import com.microforum.posts.service.interfaces.PostService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    public static final String NOTIFICATION_SENDER_EXCHANGE = "notificationSender-out-0";
    private final PostRepository postRepository;
    private final TagServiceImpl tagService;
    private final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private final StreamBridge streamBridge;

    /**
     * Create a new post in the database
     * @param postDTO a data transfer model to create a new post
     * @return the created post
     */
    @Override
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
                .tags(tagService.getTagsListByNames(postDTO.getTags()))
                .ageFlag(setAgeFlagByAnalyzerService())
                .reviewCount(0L)
                .status(setStatus())
            .build();
        logger.info("Saving post: {}", post);
        var result = postRepository.save(post);
        logger.info("Saved post: {}", result);
        sendNotificationToFollowers(post);
        return result;
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
        return Status.PUBLISHED;
    }

    /**
     * Get a post by its id
     * @param id the id of the post
     * @return the post
     * @throws ResourceNotFoundException if the post is not found
     */
    @Override
    public Post getPostById(Long id) throws ResourceNotFoundException {
        // TODO: throw custom exception
        return postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", " where postId = " + id));
    }

    /**
     * Update a post by its id
     * @param postId the id of the post
     * @param postDTO a data transfer model to update the post
     * @return the updated post
     * @throws ResourceNotFoundException if the post is not found
     */
    @Override
    @Transactional
    public Post update(Long postId, UpdatePostDTO postDTO) throws ResourceNotFoundException {
        validateUserRight();
        Post post = getPostById(postId);
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setCategory(postDTO.getCategory());
        post.setSubCategory(postDTO.getSubCategory());
        post.setTags(tagService.getTagsListByNames(postDTO.getTags()));
        logger.info("Updating post: {}", post);
        var result = postRepository.save(post);
        logger.info("Updated post: {}", result);
        return postRepository.save(post);
    }

    /**
     * Delete a post by its id
     * @param id the id of the post
     * @throws ResourceNotFoundException if the post is not found
     */
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
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

    /**
     * Get all posts
     * @return a list of posts
     */
    @Override
    public List<Post> getAllPost(int page, int size) {
        var result = postRepository
                .findAllPosts(getPageRequest(page, size))
                .getContent();
        logger.info("Getting all posts, result: {}", result);

        result = result.stream()
                .filter(this::filterPosts)
                .toList();
        logger.info("Filtered all posts, result: {}", result);

        return result;
    }

    /**
     * Get all posts by a user
     * @param userId the id of the user
     * @return a list of posts
     */
    @Override
    public List<Post> getPostByUserId(Long userId, int page, int size) {
        var result = postRepository
                .findByUserId(userId, getPageRequest(page, size))
                .getContent();
        logger.info("Getting post by user id: {} result: {}", userId, result);

        result = result.stream()
                .filter(this::filterPosts)
                .toList();
        logger.info("Filtered posts by user id: {} result: {}", userId, result);

        return result;
    }

    /**
     * Get posts by category
     * @param category the category of the post
     * @return a list of posts
     */
    @Override
    public List<Post> getPostByCategory(String category, int page, int size) {
        var result = postRepository
                .findByCategory(category, getPageRequest(page, size))
                .getContent();
        logger.info("Getting post by category: {} result: {}", category, result);

        result = result.stream()
                .filter(this::filterPosts)
                .toList();
        logger.info("Filtered posts by category: {} result: {}", category, result);

        return result;
    }

    private boolean filterPosts(Post post) {
        if (post.getStatus() == Status.DELETED) {
            return false;
        }
        // TODO: Filter by status, age flag, own post
        return true;
    }

    private Pageable getPageRequest(int page, int size) {
        return PageRequest.of(page - 1, size);
    }

    private void sendNotificationToFollowers(Post post) {
        var followersInfo = getFollowersInfo(post.getUserId());
        followersInfo.forEach(userInfo -> {
            var notification = buildNotification(post, userInfo);
            logger.info("Sending notification: {} for post {}", notification, post);
            boolean result = streamBridge.send(NOTIFICATION_SENDER_EXCHANGE, notification);
            logger.info("Post notification request successfully triggered ? : {}", result);
        });
    }

    private List<Map<String, Object>> getFollowersInfo(Long userId) {
        // Call user service and parse information
        // TODO: Add event driven here
        List<Long> followers = new ArrayList<>();
        for (int i=0; i<random.nextInt(10); i++) {
            followers.add(random.nextLong(10));
        }
        logger.info("Followers userId's of user: {} are {}", userId, followers);
        List<Map<String, Object>> followersInfo = new ArrayList<>();
        for (Long followerUserId : followers) {
            followersInfo.add(getUserContactInfo(followerUserId));
        }
        logger.info("Followers info of user: {} are {}", userId, followersInfo);
        return followersInfo;
    }

    private Map<String, Object> getUserContactInfo(Long userId) {
        return Map.of("id", userId,
                "email", "user_" + userId + "_name" + random.nextInt(100) + "@gmail.com",
                "phoneNo", "+880 1" + (3+random.nextInt(7)) + (random.nextInt(99_999_999) + 100_000_000));
    }

    private Object buildNotification(Post post, Map<String, Object> userInfo) {
        return DynamicModel.builder()
                .addParam("title", "New post from " + post.getUserFullName() + "!")
                .addParam("message", post.getContent().substring(Math.min(30, post.getContent().length())))
                .addParam("url", "http://localhost:9999/micro-forum/posts/posts/" + post.getId())
                .addParam("prefix", null)
                .addParam("suffix", "Hurry up and be the first to comment!")
                .addParam("durationInSeconds", 1000 * 60 * 60 * 24 * 7) // 7 days
                .addParam("userId", userInfo.get("id"))
                .addParam("email", userInfo.get("email"))
                .addParam("phoneNo", userInfo.get("phoneNo"))
                .addParam("notificationTypes", List.of("SMS", "PUSH", "EMAIL"))
                .addParam("source", "post-service")
                .build();
    }
}
