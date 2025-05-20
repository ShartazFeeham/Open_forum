package com.open.forum.review.domain.external.client;

import com.open.forum.review.shared.PostPrivacy;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface PostServiceClient {
    /**
     * Retrieves the privacy settings of a post.
     *
     * @param postId the ID of the post
     * @return the privacy settings of the post
     */
    PostPrivacy getPostPrivacy(@NotNull @Positive Long postId);
}
