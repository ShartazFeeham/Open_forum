package com.open.forum.review.infrastructure.repository.comment;

import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.repository.CommentRepository;
import com.open.forum.review.infrastructure.cache.core.CrudCacheTemplate;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl extends CrudCacheTemplate<Long, Comment> implements CommentRepository {

    protected CommentRepositoryImpl(RedissonClient redissonClient) {
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

    /**
     * Finds all comments for a specific post.
     *
     * @param postId the ID of the post
     * @param page   the page number for pagination
     * @param size   the number of comments per page
     * @return a list of comments for the post
     */
    @Override
    public List<Comment> findCommentsByPostId(@NotNull @Positive Long postId, @Min(0) int page, @Min(1) int size) {
        return List.of();
    }

    /**
     * Finds all comments made by a specific user.
     *
     * @param userId the ID of the user
     * @param page
     * @param size
     * @return a list of comments made by the user
     */
    @Override
    public List<Comment> findCommentsByUserId(@NotNull @Positive Long userId, @Min(0) int page, @Min(1) int size) {
        return List.of();
    }

    @Override
    protected Optional<Comment> getFromSource(Long key) {
        return null;
    }

    @Override
    protected Comment createToSource(Comment comment) {
        return null;
    }

    @Override
    protected Comment updateToSource(Long key, Comment comment) {
        return null;
    }

    @Override
    protected Comment deleteToSource(Long key) {
        return null;
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
