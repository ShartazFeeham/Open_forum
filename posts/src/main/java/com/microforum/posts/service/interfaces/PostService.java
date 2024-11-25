package com.microforum.posts.service.interfaces;

import com.microforum.posts.entity.Post;
import com.microforum.posts.exception.ResourceNotFoundException;
import com.microforum.posts.models.CreatePostDTO;
import com.microforum.posts.models.UpdatePostDTO;

import java.util.List;

public interface PostService {
    /**
     * Create a new post in the database
     * @param postDTO a data transfer model to create a new post
     * @return the created post
     */
    Post create(CreatePostDTO postDTO);

    /**
     * Get a post by its id
     * @param id the id of the post
     * @return the post
     * @throws ResourceNotFoundException if the post is not found
     */
    Post getPostById(Long id) throws ResourceNotFoundException;

    /**
     * Update a post by its id
     * @param postId the id of the post
     * @param postDTO a data transfer model to update the post
     * @return the updated post
     * @throws ResourceNotFoundException if the post is not found
     */
    Post update(Long postId, UpdatePostDTO postDTO) throws ResourceNotFoundException;

    /**
     * Delete a post by its id
     * @param id the id of the post
     * @throws ResourceNotFoundException if the post is not found
     */
    void delete(Long id) throws ResourceNotFoundException;

    /**
     * Get all posts
     * @return a list of posts
     */
    List<Post> getAllPost(int page, int size);

    /**
     * Get all posts by a user
     * @param userId the id of the user
     * @return a list of posts
     */
    List<Post> getPostByUserId(Long userId, int page, int size);

    /**
     * Get posts by category
     * @param category the category of the post
     * @return a list of posts
     */
    List<Post> getPostByCategory(String category, int page, int size);
}
