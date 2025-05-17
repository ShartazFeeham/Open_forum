package com.open.forum.review.application.mapper;

import com.open.forum.review.application.dto.reaction.ReactionCreateDTO;
import com.open.forum.review.application.dto.reaction.ReactionReadDTO;
import com.open.forum.review.application.dto.reaction.ReactionUpdateDTO;
import com.open.forum.review.domain.model.reaction.Reaction;
import com.open.forum.review.domain.model.reaction.ReactionStatus;
import com.open.forum.review.shared.utility.ServerZonedDateTime;

import java.time.ZonedDateTime;

/*
 * This class is responsible for mapping between different data transfer objects (DTOs) and domain models related to reactions.
 * It provides methods to convert between ReactionCreateDTO and ReactionReadDTO, as well as to map a list of ReactionReadDTOs.
 */
public class ReactionMapper {

    /**
     * Converts a ReactionCreateDTO to a ReactionReadDTO.
     *
     * @param dto the ReactionCreateDTO to convert
     * @return the converted ReactionReadDTO
     */
    public static Reaction toReaction(ReactionCreateDTO dto) {
        return Reaction.builder()
                .postId(dto.postId())
                .commentId(dto.commentId())
                .userId(dto.userId())
                .reactionStatus(ReactionStatus.PENDING)
                .reactionType(dto.reactionType())
                .createdAt(ServerZonedDateTime.now())
                .build();
    }

    /**
     * Converts a ReactionUpdateDTO to a Reaction entity.
     *
     * @param dto the ReactionUpdateDTO to convert
     * @param reaction the existing Reaction entity to update
     * @return the updated Reaction entity
     */
    public static Reaction toReaction(ReactionUpdateDTO dto, Reaction reaction) {
        reaction.setReactionType(dto.reactionType());
        return reaction;
    }

    /**
     * Converts a Reaction entity to a ReactionReadDTO.
     *
     * @param reaction the Reaction entity to convert
     * @return the converted ReactionReadDTO
     */
    public static ReactionReadDTO toReactionReadDTO(Reaction reaction) {
        return ReactionReadDTO.from(reaction);
    }
}
