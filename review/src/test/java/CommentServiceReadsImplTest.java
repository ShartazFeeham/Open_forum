import com.open.forum.review.application.dto.comment.CommentReadDTO;
import com.open.forum.review.application.mapper.CommentMapper;
import com.open.forum.review.application.service.CommentServiceReadsImpl;
import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.repository.CommentRepository;
import com.open.forum.review.shared.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceReadsImplTest {

    @Mock
    private CommentRepository repository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentServiceReadsImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRead_Success() {
        Long commentId = 1L;
        Comment comment = mock(Comment.class);
        CommentReadDTO dto = mock(CommentReadDTO.class);

        when(repository.findCommentById(commentId)).thenReturn(Optional.of(comment));
        when(comment.isReadAllowed()).thenReturn(true);

        CommentReadDTO result = service.read(commentId);

        assertNotNull(result);
        verify(repository).findCommentById(commentId);
        verify(comment).isReadAllowed();
    }

    @Test
    void testRead_CommentNotFound() {
        Long commentId = 1L;

        when(repository.findCommentById(commentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.read(commentId));

        assertEquals("Comment not found with ID: " + commentId, exception.getMessage());
        verify(repository).findCommentById(commentId);
    }

    @Test
    void testRead_CommentNotReadable() {
        Long commentId = 1L;
        Comment comment = mock(Comment.class);

        when(repository.findCommentById(commentId)).thenReturn(Optional.of(comment));
        when(comment.isReadAllowed()).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.read(commentId));

        assertEquals("Comment not found with ID: " + commentId, exception.getMessage());
        verify(repository).findCommentById(commentId);
        verify(comment).isReadAllowed();
    }

    @Test
    void testReadByUser_Success() {
        Long userId = 1L;
        int page = 0;
        int size = 10;
        Comment comment = mock(Comment.class);
        CommentReadDTO dto = mock(CommentReadDTO.class);

        when(repository.findCommentsByUserId(userId, page, size)).thenReturn(List.of(comment));
        when(comment.isReadAllowed()).thenReturn(true);

        List<CommentReadDTO> result = service.readByUser(userId, page, size);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findCommentsByUserId(userId, page, size);
        verify(comment).isReadAllowed();
    }

    @Test
    void testReadByUser_NoReadableComments() {
        Long userId = 1L;
        int page = 0;
        int size = 10;
        Comment comment = mock(Comment.class);

        when(repository.findCommentsByUserId(userId, page, size)).thenReturn(List.of(comment));
        when(comment.isReadAllowed()).thenReturn(false);

        List<CommentReadDTO> result = service.readByUser(userId, page, size);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findCommentsByUserId(userId, page, size);
        verify(comment).isReadAllowed();
    }

    @Test
    void testReadByPost_Success() {
        Long postId = 1L;
        int page = 0;
        int size = 10;
        Comment comment = mock(Comment.class);
        CommentReadDTO dto = mock(CommentReadDTO.class);

        when(repository.findCommentsByPostId(postId, page, size)).thenReturn(List.of(comment));
        when(comment.isReadAllowed()).thenReturn(true);

        List<CommentReadDTO> result = service.read(postId, page, size);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findCommentsByPostId(postId, page, size);
        verify(comment).isReadAllowed();
    }

    @Test
    void testReadByPost_NoReadableComments() {
        Long postId = 1L;
        int page = 0;
        int size = 10;
        Comment comment = mock(Comment.class);

        when(repository.findCommentsByPostId(postId, page, size)).thenReturn(List.of(comment));
        when(comment.isReadAllowed()).thenReturn(false);

        List<CommentReadDTO> result = service.read(postId, page, size);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository).findCommentsByPostId(postId, page, size);
        verify(comment).isReadAllowed();
    }
}