package com.open.forum.review.infrastructure.external;

import com.open.forum.review.domain.external.client.PostServiceClient;
import com.open.forum.review.infrastructure.cache.core.SingleReadCacheTemplate;
import com.open.forum.review.infrastructure.repository.post.PostPrivacyEntity;
import com.open.forum.review.infrastructure.repository.post.PostPrivacyJpaRepository;
import com.open.forum.review.shared.enums.PostPrivacy;
import jakarta.validation.constraints.NotNull;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class PostPrivacyRepositoryImpl extends
        SingleReadCacheTemplate<Long, PostPrivacy> implements PostServiceClient {

    private final PostPrivacyJpaRepository postPrivacyJpaRepository;

    public PostPrivacyRepositoryImpl(PostPrivacyJpaRepository postPrivacyJpaRepository, RedissonClient redissonClient) {
        super(redissonClient);
        this.postPrivacyJpaRepository = postPrivacyJpaRepository;
    }

    /**
     * Retrieves the privacy settings of a post.
     *
     * @param postId the ID of the post
     * @return the privacy settings of the post
     */
    @Override
    public PostPrivacy getPostPrivacy(@NotNull Long postId) {
        return this.read(postId);
    }

    @Override
    protected String cacheKey(Long postId) {
        return "post-privacy-postId:" + postId;
    }

    @Override
    protected PostPrivacy getFromSource(Long postId) {
        return postPrivacyJpaRepository
                .findById(postId)
                .map(PostPrivacyEntity::getPostPrivacy)
                .orElse(null);
    }

    @Override
    protected Long expirationTimeInSeconds() {
        return 300L; // 5 minutes
    }
}
