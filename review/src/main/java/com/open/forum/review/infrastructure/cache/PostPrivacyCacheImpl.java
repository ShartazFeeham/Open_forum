package com.open.forum.review.infrastructure.cache;

import com.open.forum.review.domain.cache.PostPrivacyCache;
import com.open.forum.review.domain.external.client.PostServiceClient;
import com.open.forum.review.infrastructure.cache.core.SingleReadCacheTemplate;
import com.open.forum.review.infrastructure.external.PostPrivacyDatabaseImpl;
import com.open.forum.review.shared.PostPrivacy;
import jakarta.validation.constraints.NotNull;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostPrivacyCacheImpl extends SingleReadCacheTemplate<Long, PostPrivacy> implements PostPrivacyCache {

    private final Logger log = LoggerFactory.getLogger(PostPrivacyCacheImpl.class);
    private final PostServiceClient postServiceClient;

    protected PostPrivacyCacheImpl(RedissonClient redissonClient, PostPrivacyDatabaseImpl postServiceClient) {
        super(redissonClient);
        this.postServiceClient = postServiceClient;
    }

    /**
     * Retrieves the privacy settings of a post.
     *
     * @param postId the ID of the post
     * @return the privacy settings of the post
     */
    @Override
    public PostPrivacy getPostPrivacy(@NotNull Long postId) {
        PostPrivacy postPrivacy = this.read(postId);
        if (postPrivacy == null) {
            log.warn("Post privacy not found for postId: {}", postId);
        }
        return postPrivacy;
    }

    @Override
    protected String cacheKey(Long postId) {
        return "post_privacy:" + postId;
    }

    @Override
    protected PostPrivacy getFromSource(Long postId) {
        log.info("Requesting post privacy from database for postId: {}", postId);
        PostPrivacy postPrivacy = postServiceClient.getPostPrivacy(postId);
        if (postPrivacy == null) {
            log.warn("Post privacy not found in database for postId: {}", postId);
        } else {
            log.info("Post privacy retrieved from database for postId: {}: {}", postId, postPrivacy);
        }
        return postPrivacy;
    }

    @Override
    protected Long expirationTimeInSeconds() {
        // Cache expiration time is set to 1 hour (3600 seconds)
        return 3600L;
    }
}
