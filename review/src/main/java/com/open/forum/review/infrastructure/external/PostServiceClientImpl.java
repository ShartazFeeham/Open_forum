package com.open.forum.review.infrastructure.external;

import com.open.forum.review.domain.external.client.PostServiceClient;
import com.open.forum.review.shared.PostPrivacy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PostServiceClientImpl implements PostServiceClient {
    /**
     * Retrieves the privacy settings of a post.
     *
     * @param postId the ID of the post
     * @return the privacy settings of the post
     */
    @Override
    public PostPrivacy getPostPrivacy(Long postId) {
        return null;
    }

    /**
     * Checks if a post exists.
     *
     * @param postId the ID of the post
     * @return true if the post exists, false otherwise
     */
    @Override
    public boolean isPostExist(Long postId) {
        return false;
    }

    /**
     * Retrieves the details of a post.
     *
     * @param postId the ID of the post
     * @return a map containing the details of the post
     */
    @Override
    public Map<String, Object> getPostById(Long postId) {
        return Map.of();
    }
}
