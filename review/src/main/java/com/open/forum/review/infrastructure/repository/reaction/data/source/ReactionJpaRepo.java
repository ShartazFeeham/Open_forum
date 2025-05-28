package com.open.forum.review.infrastructure.repository.reaction.data.source;

import com.open.forum.review.infrastructure.repository.reaction.entity.ReactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionJpaRepo extends JpaRepository<ReactionEntity, Long> {

    @Query("SELECT r FROM ReactionEntity r WHERE r.postId = :postId")
    List<ReactionEntity> findByPostId(@Param("postId") Long postId);

    @Query("SELECT r FROM ReactionEntity r WHERE r.commentId = :commentId")
    List<ReactionEntity> findByCommentId(@Param("commentId") Long commentId);
}