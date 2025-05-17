package com.open.forum.review.presentation.controllers.comment;

import com.open.forum.review.application.service.ports.CommentServiceReads;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentReadsController {

    private final CommentServiceReads commentServiceReads;

    public CommentReadsController(CommentServiceReads commentServiceReads) {
        this.commentServiceReads = commentServiceReads;
    }


}
