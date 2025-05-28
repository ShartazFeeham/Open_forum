package com.open.forum.review.infrastructure.repository.comment.data.source;

import com.open.forum.review.infrastructure.repository.comment.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentJpaRepo extends JpaRepository<CommentEntity, Long> {

    // find by postId with pagination
    @Query("SELECT c FROM CommentEntity c WHERE c.postId = :postId")
    Page<CommentEntity> findByPostId(Long postId, Pageable pageable);

    // find by userId with pagination
    @Query("SELECT c FROM CommentEntity c WHERE c.userId = :userId")
    Page<CommentEntity> findByUserId(Long userId, Pageable pageable);
}
