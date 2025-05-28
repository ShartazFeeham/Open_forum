package com.open.forum.review.infrastructure.repository.reaction.aggregator;

import com.open.forum.review.domain.model.reaction.Reaction;
import com.open.forum.review.domain.repository.ReactionRepository;
import com.open.forum.review.infrastructure.cache.core.CrudCacheTemplate;
import org.redisson.api.RedissonClient;

import java.util.Optional;

public abstract class ReactionRepositoryAndCacheAggregator extends
        CrudCacheTemplate<Long, Reaction> implements ReactionRepository {

    protected ReactionRepositoryAndCacheAggregator(RedissonClient redissonClient) {
        super(redissonClient);
    }

    /**
     * Saves a new reaction.
     *
     * @param reaction the reaction to save
     */
    @Override
    public void saveReaction(Reaction reaction) {
        this.create(reaction.getReactionId(), reaction);
    }

    /**
     * Finds a reaction by its ID.
     *
     * @param reactionId the ID of the reaction
     * @return the found reaction, or null if not found in an optional
     */
    @Override
    public Optional<Reaction> findReactionById(Long reactionId) {
        return this.read(reactionId);
    }

    /**
     * Updates an existing reaction.
     *
     * @param reaction the reaction to update
     */
    @Override
    public void updateReaction(Reaction reaction) {
        this.update(reaction.getReactionId(), reaction);
    }

    /**
     * Deletes a reaction by its ID.
     *
     * @param reactionId the ID of the reaction to delete
     */
    @Override
    public void deleteReaction(Long reactionId) {
        this.delete(reactionId);
    }

    @Override
    protected Long expirationTimeInSeconds() {
        // Cache expiration time is set to 5 minutes (300 seconds)
        return 300L;
    }

    @Override
    protected String cacheKey(Long reactionId) {
        return "reaction-crud:" + reactionId;
    }
}
