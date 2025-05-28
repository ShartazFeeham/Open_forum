package com.open.forum.review.infrastructure.repository.comment.aggregator;

import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.repository.CommentRepository;
import com.open.forum.review.infrastructure.cache.core.CrudCacheTemplate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.redisson.api.RedissonClient;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

public abstract class CommentRepositoryAndCacheAggregator extends
        CrudCacheTemplate<Long, Comment> implements CommentRepository {

    protected CommentRepositoryAndCacheAggregator(RedissonClient redissonClient) {
        super(redissonClient);
    }

    @Override
    public void saveComment(@Validated Comment comment) {
        this.create(comment.getCommentId(), comment);
    }

    @Override
    public Optional<Comment> findCommentById(@NotNull @Positive Long commentId) {
        return this.read(commentId);
    }

    @Override
    public void updateComment(@Validated Comment comment) {
        this.update(comment.getCommentId(), comment);
    }

    @Override
    public void deleteComment(@NotNull @Positive Long commentId) {
        this.delete(commentId);
    }

    @Override
    protected Long expirationTimeInSeconds() {
        // Cache expiration time is set to 5 minutes (300 seconds)
        return 300L;
    }

    @Override
    protected String cacheKey(Long commentId) {
        return "comment-crud:" + commentId;
    }
}
