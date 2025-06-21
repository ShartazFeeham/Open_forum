package com.open.forum.review.application.service;

import com.open.forum.review.application.dto.reaction.ReactionCreateDTO;
import com.open.forum.review.application.dto.reaction.ReactionUpdateDTO;
import com.open.forum.review.application.mapper.ReactionMapper;
import com.open.forum.review.application.service.ports.ReactionServiceWrites;
import com.open.forum.review.domain.cache.PostPrivacyCache;
import com.open.forum.review.domain.cache.UserExistenceCache;
import com.open.forum.review.domain.events.producer.ReactionEventProducer;
import com.open.forum.review.domain.events.reaction.ReactionCreatedEvent;
import com.open.forum.review.domain.model.comment.CommentStatus;
import com.open.forum.review.domain.model.reaction.Reaction;
import com.open.forum.review.domain.model.reaction.ReactionStatus;
import com.open.forum.review.domain.repository.CommentRepository;
import com.open.forum.review.domain.repository.ReactionRepository;
import com.open.forum.review.shared.PostPrivacy;
import com.open.forum.review.shared.exception.IllegalRequestException;
import com.open.forum.review.shared.exception.TaskNotCompletableException;
import com.open.forum.review.shared.helper.TokenExtractor;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionServiceWritesImpl implements ReactionServiceWrites {

    private final ReactionRepository repository;
    private final PostPrivacyCache postPrivacyCache;
    private final CommentRepository commentRepository;
    private final ReactionEventProducer reactionEventProducer;
    private final UserExistenceCache userExistenceCache;
    private static final Logger log = LoggerFactory.getLogger(ReactionServiceWritesImpl.class);

    /**
     * Creates a new reaction.
     *
     * @param dto the data transfer object containing the details of the reaction to be created
     * @return the created reaction
     */
    @Override
    public Reaction create(ReactionCreateDTO dto) {
        dto.validateOrThrow();
        Reaction reaction = ReactionMapper.toReaction(dto);
        validateUserExistence(reaction);
        checkPrivacy(reaction);
        reaction.setReactionStatus(ReactionStatus.APPROVED);
        try {
            repository.saveReaction(reaction);
            log.info("Reaction saved successfully: {}", reaction);
            publishReactionCreatedEvent(reaction);
        } catch (Exception e) {
            log.error("Error saving reaction: {}", e.getMessage());
            throw new TaskNotCompletableException("Cannot perform this action right now." +
                    (reaction.getPostId() == null ? "comment" : "post") + " may not exit! Or please try again later.");
        }
        return null;
    }

    private void validateUserExistence(Reaction reaction) {
        Boolean isUserExist = userExistenceCache.isUserExist(reaction.getUserId());
        if (isUserExist == null) {
            log.error("User existence check failed for userId: {}", reaction.getUserId());
            throw new TaskNotCompletableException("Cannot perform this action right now, please try again later.");
        }
        if (!isUserExist) {
            log.error("User does not exist: {}", reaction.getUserId());
            throw new IllegalRequestException("User does not exist.");
        }
    }

    private void checkPrivacy(Reaction reaction) {
        if (reaction.getPostId() == null) {
            checkForCommentPrivacy(reaction);
        } else {
            checkForPostPrivacy(reaction);
        }
    }

    private void checkForCommentPrivacy(Reaction reaction) {
        commentRepository.findCommentById(reaction.getCommentId()).ifPresentOrElse(
                comment -> {
                    log.info("Comment found: {}", comment);
                    if (comment.getStatus() != CommentStatus.PUBLISHED) {
                        log.error("Comment current status: {}, can not create reaction for commentId: {}", comment
                                .getStatus(), comment.getCommentId());
                        throw new TaskNotCompletableException("Cannot perform this action right now because the " +
                                "comment is not available, please try again later.");
                    }
                }, () -> {
                    log.error("Comment not found for commentId: {}", reaction.getCommentId());
                    throw new TaskNotCompletableException("Cannot perform this action right now because the comment " +
                            "is not available, please try again later.");
                }
        );
    }

    private void checkForPostPrivacy(Reaction reaction) {
        PostPrivacy postPrivacy = postPrivacyCache.getPostPrivacy(reaction.getPostId());
        if (Objects.isNull(postPrivacy)) {
            log.error("Post privacy not found for postId: {}", reaction.getPostId());
            throw new TaskNotCompletableException("Cannot perform this action right now, please try again later.");
        }
        if (postPrivacy == PostPrivacy.REVIEW_NOT_ALLOWED) {
            log.error("Commenting is not allowed on a private post: {}", reaction.getPostId());
            throw new IllegalRequestException("Commenting is not allowed on a private post.");
        }
    }

    private void publishReactionCreatedEvent(Reaction reaction) {
        final ReactionCreatedEvent event = new ReactionCreatedEvent(reaction);
        reactionEventProducer.publish(event);
        log.info("Reaction created event published: {}", event);
    }

    /**
     * Updates an existing reaction.
     *
     * @param dto the data transfer object containing the updated details of the reaction
     */
    @Override
    public void update(ReactionUpdateDTO dto) {
        Optional<Reaction> reactionOp = repository.findReactionById(dto.reactionId());
        reactionOp.ifPresentOrElse(reaction -> {
            dto.validateOrThrow(dto.reactionId(), reaction);
            reaction.setReactionType(dto.reactionType());
            try {
                checkForUserRight(reaction, "update");
                repository.updateReaction(reaction);
                log.info("Reaction updated successfully: {}", reaction);
            } catch (Exception e) {
                log.error("Error updating reaction: {}", e.getMessage());
                throw new TaskNotCompletableException("Cannot perform this action right now." +
                        (reaction.getPostId() == null ? "comment" : "post") + " may not exit! Or please try again later.");
            }
        }, () -> {
            log.error("Update failed, reaction not found with ID: {}", dto.reactionId());
            throw new IllegalRequestException("Reaction not found with ID: " + dto.reactionId());
        });
    }

    /**
     * Deletes a reaction by its ID.
     *
     * @param reactionId the ID of the reaction to be deleted
     */
    @Override
    public void deleteReaction(@NotNull Long reactionId) {
        final var reactionOptional = repository.findReactionById(reactionId);
        reactionOptional.ifPresentOrElse(
                reaction -> {
                    try {
                        checkForUserRight(reaction, "delete");
                        repository.deleteReaction(reactionId);
                        log.info("Reaction deleted successfully: {}", reaction);
                    } catch (Exception e) {
                        log.error("Error deleting reaction: {}, error: {}", reaction, e.getMessage());
                        throw new TaskNotCompletableException("Cannot perform this action right now." +
                                (reaction.getPostId() == null ? "comment" : "post") + " may not exit! Or please try again later.");
                    }
                }, () -> {
                    log.error("Reaction not found with ID: {}", reactionId);
                    throw new IllegalRequestException("Reaction not found with ID: " + reactionId);
                }
        );
    }

    private void checkForUserRight(Reaction reaction, String action) {
        boolean matchUserId = TokenExtractor.matchUserId(reaction.getUserId());
        if (!matchUserId) {
            log.error("User does not have permission to {} this comment: {}", action, reaction.getUserId());
            throw new IllegalRequestException("You do not have permission to " + action + " this comment.");
        }
    }
}
