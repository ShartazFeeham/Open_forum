package com.open.forum.review.application.dto.reaction;

import com.open.forum.review.domain.model.reaction.Reaction;

public class ReactionReadDTO extends Reaction {

    public static ReactionReadDTO from(Reaction reaction) {
        return (ReactionReadDTO) reaction;
    }
}
