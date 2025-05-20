package com.open.forum.review.infrastructure.external;

import com.open.forum.review.domain.external.client.PostServiceClient;
import com.open.forum.review.infrastructure.repository.post.PostPrivacyEntity;
import com.open.forum.review.infrastructure.repository.post.PostPrivacyJpaRepository;
import com.open.forum.review.shared.PostPrivacy;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class PostPrivacyRepositoryImpl implements PostServiceClient {

    private final PostPrivacyJpaRepository postPrivacyJpaRepository;

    public PostPrivacyRepositoryImpl(PostPrivacyJpaRepository postPrivacyJpaRepository) {
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
        return postPrivacyJpaRepository
                .findById(postId)
                .map(PostPrivacyEntity::getPostPrivacy)
                .orElse(null);
    }

    public PostPrivacyEntity save(PostPrivacyEntity postPrivacyEntity) {
        return postPrivacyJpaRepository.save(postPrivacyEntity);
    }
}
