package com.open.forum.review.infrastructure.cache;

import com.open.forum.review.domain.cache.PostPrivacyCache;
import com.open.forum.review.shared.PostPrivacy;
import org.springframework.stereotype.Service;

@Service
public class PostPrivacyCacheImpl implements PostPrivacyCache {
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
}
