package com.open.forum.review.infrastructure.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPrivacyJpaRepository extends JpaRepository<PostPrivacyEntity, Long> {
}
