package com.open.forum.review.domain.repository;

import com.open.forum.review.domain.model.reaction.Reaction;
import com.open.forum.review.domain.model.reaction.ReactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReactionRepository {

    /**
     * Saves a reaction to a comment or post.
     *
     * @param reaction the reaction to save
     */
    void saveReaction(@Validated Reaction reaction);

    /**
     * Finds a reaction by its ID.
     *
     * @param reactionId the ID of the reaction
     * @return the found reaction, or null if not found in an optional
     */
    Optional<Reaction> findReactionById(@NotNull @Positive Long reactionId);

    /**
     * Finds all reactions for a specific post.
     *
     * @param postId the ID of the post
     * @return a list of reactions for the post
     */
    List<Reaction> findReactionsByPostId(@NotNull @Positive Long postId, @Min(0) int page, @Min(1) int size);

    /**
     * Finds all reactions for a specific post and counts them by type.
     *
     * @param postId the ID of the post
     * @return a map of reaction types and their counts
     */
    Map<ReactionType, Integer> findReactionsCountByPostId(@NotNull @Positive Long postId);

    /**
     * Finds all reactions for a specific comment.
     *
     * @param commentId the ID of the comment
     * @return a list of reactions for the comment
     */
    List<Reaction> findReactionsByCommentId(@NotNull @Positive Long commentId);

    /**
     * Finds all reactions for a specific comment and counts them by type.
     *
     * @param postId the ID of the post
     * @return a map of reaction types and their counts
     */
    Map<ReactionType, Integer> findReactionsCountByCommentId(@NotNull @Positive Long postId);

    /**
     * Updates an existing reaction.
     *
     * @param reaction the reaction to update
     */
    void updateReaction(@Validated Reaction reaction);

    /**
     * Deletes a reaction by its ID.
     *
     * @param reactionId the ID of the reaction to delete
     */
    void deleteReaction(@NotNull @Positive Long reactionId);
}
