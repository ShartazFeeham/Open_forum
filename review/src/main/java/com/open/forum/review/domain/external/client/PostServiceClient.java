package com.open.forum.review.domain.external.client;

import com.open.forum.review.shared.PostPrivacy;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.NonNull;

import java.util.Map;

public interface PostServiceClient {
    /**
     * Retrieves the privacy settings of a post.
     *
     * @param postId the ID of the post
     * @return the privacy settings of the post
     */
    PostPrivacy getPostPrivacy(@NonNull @Positive Long postId);

    /**
     * Checks if a post exists.
     *
     * @param postId the ID of the post
     * @return true if the post exists, false otherwise
     */
    boolean isPostExist(@NonNull @Positive Long postId);

    /**
     * Retrieves the details of a post.
     *
     * @param postId the ID of the post
     * @return a map containing the details of the post
     */
    Map<String, Object> getPostById(@NonNull @Positive Long postId);
}
