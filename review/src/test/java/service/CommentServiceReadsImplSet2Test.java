package service;

import com.open.forum.review.application.dto.comment.CommentReadDTO;
import com.open.forum.review.application.service.CommentServiceReadsImpl;
import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.model.comment.CommentContent;
import com.open.forum.review.domain.model.comment.CommentStatus;
import com.open.forum.review.domain.repository.CommentRepository;
import com.open.forum.review.shared.exception.EntityNotFoundException;
import com.open.forum.review.shared.utility.ServerZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceReadsImplSet2Test {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceReadsImpl commentServiceReads;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readComment_Success() {
        // Arrange
        Long commentId = 1L;
        Comment mockComment = new Comment();
        mockComment.setCommentId(commentId);
        mockComment.setStatus(CommentStatus.PUBLISHED);
        CommentContent content = new CommentContent(commentId, "Test comment", ServerZonedDateTime.now());
        mockComment.setContents(new ArrayList<>(List.of(content)));
        when(commentRepository.findCommentById(commentId)).thenReturn(Optional.of(mockComment));

        // Act
        CommentReadDTO result = commentServiceReads.read(commentId);

        // Assert
        assertNotNull(result);
        assertEquals(commentId, result.getCommentId());
        assertEquals("Test comment", result.getContents().get(0).getContent());
        verify(commentRepository, times(1)).findCommentById(commentId);
    }

    @Test
    void readComment_NotFound() {
        // Arrange
        Long commentId = 1L;
        when(commentRepository.findCommentById(commentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> commentServiceReads.read(commentId));
        verify(commentRepository, times(1)).findCommentById(commentId);
    }

    @Test
    void readCommentsByUser_Success() {
        // Arrange
        Long userId = 1L;
        int page = 0;
        int size = 10;
        Comment mockComment1 = new Comment();
        mockComment1.setCommentId(1L);
        mockComment1.setStatus(CommentStatus.PUBLISHED);
        CommentContent content1 = new CommentContent(1L, "User comment 1", ServerZonedDateTime.now());
        mockComment1.setContents(new ArrayList<>(List.of(content1)));

        Comment mockComment2 = new Comment();
        mockComment2.setCommentId(2L);
        mockComment2.setStatus(CommentStatus.PUBLISHED);
        CommentContent content2 = new CommentContent(2L, "User comment 2", ServerZonedDateTime.now());
        mockComment2.setContents(new ArrayList<>(List.of(content2)));

        when(commentRepository.findCommentsByUserId(userId, page, size)).thenReturn(List.of(mockComment1, mockComment2));

        // Act
        List<CommentReadDTO> result = commentServiceReads.readByUser(userId, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("User comment 1", result.get(0).getContents().get(0).getContent());
        assertEquals("User comment 2", result.get(1).getContents().get(0).getContent());
        verify(commentRepository, times(1)).findCommentsByUserId(userId, page, size);
    }

    @Test
    void readCommentsByPost_Success() {
        // Arrange
        Long postId = 1L;
        int page = 0;
        int size = 10;
        Comment mockComment1 = new Comment();
        mockComment1.setCommentId(1L);
        mockComment1.setStatus(CommentStatus.PUBLISHED);
        CommentContent content1 = new CommentContent(1L, "Post comment 1", ServerZonedDateTime.now());
        mockComment1.setContents(new ArrayList<>(List.of(content1)));

        Comment mockComment2 = new Comment();
        mockComment2.setCommentId(2L);
        mockComment2.setStatus(CommentStatus.PUBLISHED);
        CommentContent content2 = new CommentContent(2L, "Post comment 2", ServerZonedDateTime.now());
        mockComment2.setContents(new ArrayList<>(List.of(content2)));

        when(commentRepository.findCommentsByPostId(postId, page, size)).thenReturn(List.of(mockComment1, mockComment2));

        // Act
        List<CommentReadDTO> result = commentServiceReads.read(postId, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Post comment 1", result.get(0).getContents().get(0).getContent());
        assertEquals("Post comment 2", result.get(1).getContents().get(0).getContent());
        verify(commentRepository, times(1)).findCommentsByPostId(postId, page, size);
    }
}