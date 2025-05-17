package com.open.forum.review.domain.cache;

import com.open.forum.review.shared.PostPrivacy;
import jakarta.validation.constraints.NotNull;

/**
 * Interface for caching post privacy settings.
 * This interface provides methods to retrieve the privacy settings of a post.
 */
public interface PostPrivacyCache {

    /**
     * Retrieves the privacy settings of a post.
     *
     * @param postId the ID of the post
     * @return the privacy settings of the post
     */
    PostPrivacy getPostPrivacy(@NotNull Long postId);

}
