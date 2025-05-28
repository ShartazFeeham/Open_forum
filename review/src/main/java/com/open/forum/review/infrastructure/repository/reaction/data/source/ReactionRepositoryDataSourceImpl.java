package com.open.forum.review.infrastructure.repository.reaction.data.source;

import com.open.forum.review.domain.model.reaction.Reaction;
import com.open.forum.review.domain.model.reaction.ReactionType;
import com.open.forum.review.infrastructure.repository.reaction.aggregator.ReactionRepositoryAndCacheAggregator;
import com.open.forum.review.infrastructure.repository.reaction.entity.ReactionEntity;
import com.open.forum.review.infrastructure.repository.reaction.mapper.ReactionMapper;
import com.open.forum.review.shared.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class ReactionRepositoryDataSourceImpl extends ReactionRepositoryAndCacheAggregator {

    private final ReactionJpaRepo reactionJpaRepo;

    protected ReactionRepositoryDataSourceImpl(RedissonClient redissonClient, ReactionJpaRepo reactionJpaRepo) {
        super(redissonClient);
        this.reactionJpaRepo = reactionJpaRepo;
    }

    @Override
    protected Reaction createToSource(Reaction reaction) {
        log.info("Creating a new reaction: {}", reaction);
        ReactionEntity reactionEntity = ReactionMapper.toReactionEntity(reaction);
        ReactionEntity savedEntity = reactionJpaRepo.save(reactionEntity);
        log.info("Reaction created successfully with ID: {}", savedEntity.getReactionId());
        return ReactionMapper.toReaction(savedEntity);
    }

    @Override
    protected Reaction updateToSource(Long reactionId, Reaction reaction) {
        log.info("Updating reaction with ID: {}", reactionId);
        final Reaction originalReaction = getFromSource(reactionId)
                .orElseThrow(() -> {
                    log.error("Reaction not found with ID: {}", reactionId);
                    return new EntityNotFoundException("Reaction not found with ID: " + reactionId);
                });

        log.debug("Original reaction: {}", originalReaction);
        ReactionEntity reactionEntity = ReactionMapper.toReactionEntity(reaction);
        reactionEntity.setReactionId(originalReaction.getReactionId());
        ReactionEntity savedEntity = reactionJpaRepo.save(reactionEntity);
        log.info("Reaction updated successfully with ID: {}", savedEntity.getReactionId());
        return ReactionMapper.toReaction(savedEntity);
    }

    @Override
    protected Reaction deleteToSource(Long key) {
        log.info("Deleting reaction with ID: {}", key);
        Reaction reaction = getFromSource(key)
                .orElseThrow(() -> {
                    log.error("Reaction not found with ID: {}", key);
                    return new EntityNotFoundException("Reaction not found with ID: " + key);
                });

        reactionJpaRepo.deleteById(key);
        log.info("Reaction deleted successfully with ID: {}", key);
        return reaction;
    }

    @Override
    protected Optional<Reaction> getFromSource(Long key) {
        log.info("Fetching reaction with ID: {}", key);
        Optional<Reaction> reaction = reactionJpaRepo.findById(key).map(ReactionMapper::toReaction);
        if (reaction.isPresent()) {
            log.debug("Found reaction with ID: {}: {}", key, reaction.get());
        } else {
            log.warn("No reaction found with ID: {}", key);
        }
        return reaction;
    }

    @Override
    public List<Reaction> findReactionsByPostId(Long postId, int page, int size) {
        log.info("Finding reactions for post ID: {}", postId);
        List<ReactionEntity> reactionEntities = reactionJpaRepo.findByPostId(postId);
        log.debug("Found reactions for post ID: {}: {}", postId, reactionEntities);
        return reactionEntities.stream()
                .map(ReactionMapper::toReaction)
                .collect(Collectors.toList());
    }

    @Override
    public Map<ReactionType, Integer> findReactionsCountByPostId(Long postId) {
        log.info("Counting reactions for post ID: {}", postId);
        List<ReactionEntity> reactionEntities = reactionJpaRepo.findByPostId(postId);
        Map<ReactionType, Integer> reactionCounts = reactionEntities.stream()
                .collect(Collectors.groupingBy(ReactionEntity::getReactionType, Collectors.summingInt(e -> 1)));
        log.debug("Reaction counts for post ID: {}: {}", postId, reactionCounts);
        return reactionCounts;
    }

    @Override
    public List<Reaction> findReactionsByCommentId(Long commentId) {
        log.info("Finding reactions for comment ID: {}", commentId);
        List<ReactionEntity> reactionEntities = reactionJpaRepo.findByCommentId(commentId);
        log.debug("Found reactions for comment ID: {}: {}", commentId, reactionEntities);
        return reactionEntities.stream()
                .map(ReactionMapper::toReaction)
                .collect(Collectors.toList());
    }

    @Override
    public Map<ReactionType, Integer> findReactionsCountByCommentId(Long commentId) {
        log.info("Counting reactions for comment ID: {}", commentId);
        List<ReactionEntity> reactionEntities = reactionJpaRepo.findByCommentId(commentId);
        Map<ReactionType, Integer> reactionCounts = reactionEntities.stream()
                .collect(Collectors.groupingBy(ReactionEntity::getReactionType, Collectors.summingInt(e -> 1)));
        log.debug("Reaction counts for comment ID: {}: {}", commentId, reactionCounts);
        return reactionCounts;
    }
}