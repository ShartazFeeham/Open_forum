package com.open.forum.review.infrastructure.repository;

import com.open.forum.review.domain.model.reaction.Reaction;
import com.open.forum.review.domain.model.reaction.ReactionType;
import com.open.forum.review.domain.repository.ReactionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReactionRepositoryImpl implements ReactionRepository {
    /**
     * Saves a reaction to a comment or post.
     *
     * @param reaction the reaction to save
     */
    @Override
    public void saveReaction(Reaction reaction) {

    }

    /**
     * Finds a reaction by its ID.
     *
     * @param reactionId the ID of the reaction
     * @return the found reaction, or null if not found in an optional
     */
    @Override
    public Optional<Reaction> findReactionById(Long reactionId) {
        return Optional.empty();
    }

    /**
     * Finds all reactions for a specific post.
     *
     * @param postId the ID of the post
     * @param page
     * @param size
     * @return a list of reactions for the post
     */
    @Override
    public List<Reaction> findReactionsByPostId(Long postId, int page, int size) {
        return List.of();
    }

    /**
     * Finds all reactions for a specific post and counts them by type.
     *
     * @param postId the ID of the post
     * @return a map of reaction types and their counts
     */
    @Override
    public Map<ReactionType, Integer> findReactionsCountByPostId(Long postId) {
        return Map.of();
    }

    /**
     * Finds all reactions for a specific comment.
     *
     * @param commentId the ID of the comment
     * @return a list of reactions for the comment
     */
    @Override
    public List<Reaction> findReactionsByCommentId(Long commentId) {
        return List.of();
    }

    /**
     * Finds all reactions for a specific comment and counts them by type.
     *
     * @param postId the ID of the post
     * @return a map of reaction types and their counts
     */
    @Override
    public Map<ReactionType, Integer> findReactionsCountByCommentId(Long postId) {
        return Map.of();
    }

    /**
     * Updates an existing reaction.
     *
     * @param reaction the reaction to update
     */
    @Override
    public void updateReaction(Reaction reaction) {

    }

    /**
     * Deletes a reaction by its ID.
     *
     * @param reactionId the ID of the reaction to delete
     */
    @Override
    public void deleteReaction(Long reactionId) {

    }
}
