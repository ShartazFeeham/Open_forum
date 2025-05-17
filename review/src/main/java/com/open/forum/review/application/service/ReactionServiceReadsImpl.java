package com.open.forum.review.application.service;

import com.open.forum.review.application.dto.reaction.ReactionReadDTO;
import com.open.forum.review.application.mapper.ReactionMapper;
import com.open.forum.review.application.service.ports.ReactionServiceReads;
import com.open.forum.review.domain.model.reaction.Reaction;
import com.open.forum.review.domain.model.reaction.ReactionType;
import com.open.forum.review.domain.repository.ReactionRepository;
import com.open.forum.review.shared.exception.EntityNotFoundException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
    public class ReactionServiceReadsImpl implements ReactionServiceReads {

        private final ReactionRepository repository;
        private static final Logger log = LoggerFactory.getLogger(ReactionServiceReadsImpl.class);

        public ReactionServiceReadsImpl(ReactionRepository repository) {
            this.repository = repository;
        }

        /**
         * Reads a reaction by its ID.
         *
         * @param reactionId the ID of the reaction to be read
         * @return the data transfer object containing the details of the reaction
         */
        @Override
        public ReactionReadDTO readById(@NonNull @Positive Long reactionId) {
            log.info("Reading reaction with ID: {}", reactionId);
            Reaction reaction = repository.findReactionById(reactionId)
                    .orElseThrow(() -> new EntityNotFoundException("Reaction not found with ID: " + reactionId));
            return ReactionMapper.toReactionReadDTO(reaction);
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
            return checkEmptyAndLog(repository.findReactionsByPostId(postId, page, size), List::isEmpty,
                    "Reading reactions for post with ID: " + postId,
                    "No reactions found for post with ID: " + postId,
                    "Found reactions for post with ID: " + postId);
        }

        /**
         * Retrieves the count of reactions for a given post ID.
         *
         * @param postId the ID of the post to retrieve reaction counts for
         * @return a map where the keys are reaction types and the values are their respective counts
         */
        @Override
        public Map<ReactionType, Integer> readReactionsCountByPostId(@NonNull @Positive Long postId) {
            return checkEmptyAndLog(repository.findReactionsCountByPostId(postId), Map::isEmpty,
                    "Reading reaction counts for post with ID: " + postId,
                    "No reaction counts found for post with ID: " + postId,
                    "Found reaction counts for post with ID: " + postId);
        }

        /**
         * Retrieves a list of reactions for a given comment ID.
         *
         * @param commentId the ID of the comment to retrieve reactions for
         * @return a list of reactions for the specified comment
         */
        @Override
        public List<Reaction> readReactionsByCommentId(@NonNull @Positive Long commentId) {
            return checkEmptyAndLog(repository.findReactionsByCommentId(commentId), List::isEmpty,
                    "Reading reactions for comment with ID: " + commentId,
                    "No reactions found for comment with ID: " + commentId,
                    "Found reactions for comment with ID: " + commentId);
        }

        /**
         * Retrieves the count of reactions for a given comment ID.
         *
         * @param postId the ID of the comment to retrieve reaction counts for
         * @return a map where the keys are reaction types and the values are their respective counts
         */
        @Override
        public Map<ReactionType, Integer> readReactionsCountByCommentId(@NonNull @Positive Long postId) {
            return checkEmptyAndLog(repository.findReactionsCountByCommentId(postId), Map::isEmpty,
                    "Reading reaction counts for comment with ID: " + postId,
                    "No reaction counts found for comment with ID: " + postId,
                    "Found reaction counts for comment with ID: " + postId);
        }

        private <T> T checkEmptyAndLog(T entity, Predicate<T> condition, String onRequest, String onEmpty, String onFound) {
            log.info(onRequest);
            if (condition.test(entity)) {
                log.warn(onEmpty);
            } else {
                log.info(onFound);
            }
            return entity;
        }
    }