package com.open.forum.review.application.service;

    import com.open.forum.review.application.dto.reaction.ReactionReadDTO;
    import com.open.forum.review.application.useCase.reaction.*;
    import com.open.forum.review.domain.model.reaction.Reaction;
    import com.open.forum.review.domain.model.reaction.ReactionType;
    import jakarta.validation.constraints.Min;
    import jakarta.validation.constraints.Positive;
    import org.springframework.lang.NonNull;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Map;

    @Service
    public class ReactionServiceReadsImpl implements ReadReactionsCountByCommentIdUseCase, ReadReactionByIdUseCase,
            ReadReactionByPostIdUseCase, ReadReactionCountByPostIdUseCase, ReadReactionsByCommentIdUseCase {

        /**
         * Reads a reaction by its ID.
         *
         * @param reactionId the ID of the reaction to be read
         * @return the data transfer object containing the details of the reaction
         */
        @Override
        public ReactionReadDTO readById(@NonNull @Positive Long reactionId) {
            return null;
        }

        /**
         * Retrieves a list of reactions for a given post ID.
         *
         * @param postId the ID of the post to retrieve reactions for
         * @param page   the page number to retrieve (0-indexed)
         * @param size   the number of reactions per page
         * @return a list of reactions for the specified post
         */
        @Override
        public List<Reaction> readReactionsByPostId(@NonNull @Positive Long postId, @Min(0) int page, @Min(1) int size) {
            return List.of();
        }

        /**
         * Retrieves the count of reactions for a given post ID.
         *
         * @param postId the ID of the post to retrieve reaction counts for
         * @return a map where the keys are reaction types and the values are their respective counts
         */
        @Override
        public Map<ReactionType, Integer> readReactionsCountByPostId(@NonNull @Positive Long postId) {
            return Map.of();
        }

        /**
         * Retrieves a list of reactions for a given comment ID.
         *
         * @param commentId the ID of the comment to retrieve reactions for
         * @return a list of reactions for the specified comment
         */
        @Override
        public List<Reaction> readReactionsByCommentId(@NonNull @Positive Long commentId) {
            return List.of();
        }

        /**
         * Retrieves the count of reactions for a given comment ID.
         *
         * @param postId the ID of the comment to retrieve reaction counts for
         * @return a map where the keys are reaction types and the values are their respective counts
         */
        @Override
        public Map<ReactionType, Integer> readReactionsCountByCommentId(@NonNull @Positive Long postId) {
            return Map.of();
        }
    }