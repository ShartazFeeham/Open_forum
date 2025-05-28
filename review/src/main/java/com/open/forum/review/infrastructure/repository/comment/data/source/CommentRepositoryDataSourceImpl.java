package com.open.forum.review.infrastructure.repository.comment.data.source;

import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.infrastructure.repository.comment.aggregator.CommentRepositoryAndCacheAggregator;
import com.open.forum.review.infrastructure.repository.comment.entity.CommentEntity;
import com.open.forum.review.infrastructure.repository.comment.mapper.CommentMapper;
import com.open.forum.review.shared.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class CommentRepositoryDataSourceImpl extends CommentRepositoryAndCacheAggregator {

    private final CommentJpaRepo commentJpaRepo;

    protected CommentRepositoryDataSourceImpl(RedissonClient redissonClient, CommentJpaRepo commentJpaRepo) {
        super(redissonClient);
        this.commentJpaRepo = commentJpaRepo;
    }

    @Override
    protected Comment createToSource(Comment comment) {
        log.info("Creating a new comment: {}", comment);
        CommentEntity commentEntity = CommentMapper.toCommentEntity(comment);
        CommentEntity savedEntity = commentJpaRepo.save(commentEntity);
        log.info("Comment created successfully with ID: {}", savedEntity.getCommentId());
        return CommentMapper.toComment(savedEntity);
    }

    @Override
    protected Comment updateToSource(Long commentId, Comment comment) {
        log.info("Updating comment with ID: {}", commentId);
        final Comment originalComment = getFromSource(commentId)
                .orElseThrow(() -> {
                    log.error("Comment not found with ID: {}", commentId);
                    return new EntityNotFoundException("Comment not found with ID: " + commentId);
                });

        log.debug("Original comment: {}", originalComment);
        CommentEntity commentEntity = CommentMapper.toCommentEntity(comment);
        commentEntity.setCommentId(originalComment.getCommentId());
        CommentEntity savedEntity = commentJpaRepo.save(commentEntity);
        log.info("Comment updated successfully with ID: {}", savedEntity.getCommentId());
        return CommentMapper.toComment(savedEntity);
    }

    @Override
    protected Comment deleteToSource(Long key) {
        log.info("Deleting comment with ID: {}", key);
        Comment comment = getFromSource(key)
                .orElseThrow(() -> {
                    log.error("Comment not found with ID: {}", key);
                    return new EntityNotFoundException("Comment not found with ID: " + key);
                });

        commentJpaRepo.deleteById(key);
        log.info("Comment deleted successfully with ID: {}", key);
        return comment;
    }

    @Override
    public List<Comment> findCommentsByPostId(Long postId, int page, int size) {
        log.info("Finding comments for post ID: {} with page: {} and size: {}", postId, page, size);
        Page<CommentEntity> commentEntitiesPage = commentJpaRepo.findByPostId(postId, getPageRequest(page, size));
        final var result = commentEntitiesPage.getContent();
        log.debug("Found comments for post ID: {}: {}", postId, result);
        return result.stream()
                .map(CommentMapper::toComment)
                .toList();
    }

    @Override
    public List<Comment> findCommentsByUserId(Long userId, int page, int size) {
        log.info("Finding comments for user ID: {} with page: {} and size: {}", userId, page, size);
        Page<CommentEntity> commentEntitiesPage = commentJpaRepo.findByUserId(userId, getPageRequest(page, size));
        final var result = commentEntitiesPage.getContent();
        log.debug("Found comments for user ID: {}: {}", userId, result);
        return result.stream()
                .map(CommentMapper::toComment)
                .toList();
    }

    @Override
    protected Optional<Comment> getFromSource(Long key) {
        log.info("Fetching comment with ID: {}", key);
        Optional<Comment> comment = commentJpaRepo.findById(key).map(CommentMapper::toComment);
        if (comment.isPresent()) {
            log.debug("Found comment with ID: {}: {}", key, comment.get());
        } else {
            log.warn("No comment found with ID: {}", key);
        }
        return comment;
    }

    private Pageable getPageRequest(int page, int size) {
        log.debug("Creating pageable request with page: {} and size: {}", page, size);
        return PageRequest.of(page - 1, size);
    }
}