import com.open.forum.review.application.dto.comment.CommentCreateDTO;
import com.open.forum.review.application.dto.comment.CommentUpdateDTO;
import com.open.forum.review.application.service.CommentServiceWritesImpl;
import com.open.forum.review.domain.cache.PostPrivacyCache;
import com.open.forum.review.domain.cache.UserExistenceCache;
import com.open.forum.review.domain.events.comment.CommentDeletedEvent;
import com.open.forum.review.domain.events.comment.CommentUpdatedEvent;
import com.open.forum.review.domain.events.publisher.CommentEventPublisher;
import com.open.forum.review.domain.model.comment.Comment;
import com.open.forum.review.domain.model.comment.CommentStatus;
import com.open.forum.review.domain.repository.CommentRepository;
import com.open.forum.review.shared.PostPrivacy;
import com.open.forum.review.shared.exception.IllegalRequestException;
import com.open.forum.review.shared.exception.TaskNotCompletableException;
import com.open.forum.review.shared.helper.TokenExtractor;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentServiceWritesImplTest {

    @Mock
    private CommentRepository repository;

    @Mock
    private CommentEventPublisher eventPublisher;

    @Mock
    private PostPrivacyCache postPrivacyCache;

    @Mock
    private UserExistenceCache userExistenceCache;

    @InjectMocks
    private CommentServiceWritesImpl service;

    private static MockedStatic<TokenExtractor> tokenExtractorMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeAll
    static void init() {
        tokenExtractorMock = mockStatic(TokenExtractor.class);
    }

    @AfterAll
    static void tearDown() {
        tokenExtractorMock.close();
    }

    @Test
    void testCreate_Success() {
        CommentCreateDTO dto = mock(CommentCreateDTO.class);
        Comment comment = mock(Comment.class);

        when(dto.isReply()).thenReturn(false);
        when(dto.postId()).thenReturn(1L);
        when(dto.userId()).thenReturn(1L);
        when(postPrivacyCache.getPostPrivacy(1L)).thenReturn(PostPrivacy.REVIEW_ALLOWED);
        when(userExistenceCache.isUserExist(1L)).thenReturn(true);
        doNothing().when(repository).saveComment(comment);

        Assertions.assertDoesNotThrow(() -> service.create(dto));
    }

    @Test
    void testCreate_UserDoesNotExist() {
        CommentCreateDTO dto = mock(CommentCreateDTO.class);

        when(dto.userId()).thenReturn(1L);
        when(userExistenceCache.isUserExist(1L)).thenReturn(false);

        IllegalRequestException exception = assertThrows(IllegalRequestException.class, () -> service.create(dto));
        assertEquals("User does not exist.", exception.getMessage());
    }

    @Test
    void testCreate_PostPrivacyNotAllowed() {
        CommentCreateDTO dto = mock(CommentCreateDTO.class);
        when(userExistenceCache.isUserExist(anyLong())).thenReturn(true);
        when(dto.postId()).thenReturn(1L);
        when(postPrivacyCache.getPostPrivacy(1L)).thenReturn(PostPrivacy.REVIEW_NOT_ALLOWED);

        IllegalRequestException exception = assertThrows(IllegalRequestException.class, () -> service.create(dto));
        assertEquals("Commenting is not allowed on a private post.", exception.getMessage());
    }

    @Test
    void testCreate_SaveFailure() {
        CommentCreateDTO dto = mock(CommentCreateDTO.class);
        Comment comment = mock(Comment.class);

        when(dto.postId()).thenReturn(1L);
        when(dto.userId()).thenReturn(1L);
        when(postPrivacyCache.getPostPrivacy(1L)).thenReturn(PostPrivacy.REVIEW_ALLOWED);
        when(userExistenceCache.isUserExist(1L)).thenReturn(true);
        doThrow(new RuntimeException("Database error")).when(repository).saveComment(any());

        TaskNotCompletableException exception = assertThrows(TaskNotCompletableException.class, () -> service.create(dto));
        assertTrue(exception.getMessage().contains("Cannot perform this action right now"));
    }

    @Test
    void testDelete_Success() {
        Long commentId = 1L;
        Comment comment = mock(Comment.class);

        when(repository.findCommentById(commentId)).thenReturn(Optional.of(comment));
        tokenExtractorMock.when(() -> TokenExtractor.matchUserId(comment.getUserId())).thenReturn(true);

        service.delete(commentId);

        verify(repository).deleteComment(commentId);
        verify(eventPublisher).publish(any(CommentDeletedEvent.class));
    }

    @Test
    void testDelete_CommentNotFound() {
        Long commentId = 1L;

        when(repository.findCommentById(commentId)).thenReturn(Optional.empty());

        IllegalRequestException exception = assertThrows(IllegalRequestException.class, () -> service.delete(commentId));
        assertEquals("Comment not found with ID: " + commentId, exception.getMessage());
    }

    @Test
    void testUpdate_Success() {
        CommentUpdateDTO dto = mock(CommentUpdateDTO.class);
        Comment comment = mock(Comment.class);
        Comment updatedComment = mock(Comment.class);

        when(dto.commentId()).thenReturn(1L);
        when(repository.findCommentById(1L)).thenReturn(Optional.of(comment));
        tokenExtractorMock.when(() -> TokenExtractor.matchUserId(comment.getUserId())).thenReturn(true);

        service.update(dto);

        verify(repository).updateComment(any());
        verify(eventPublisher).publish(any(CommentUpdatedEvent.class));
    }

    @Test
    void testUpdate_CommentNotFound() {
        CommentUpdateDTO dto = mock(CommentUpdateDTO.class);

        when(dto.commentId()).thenReturn(1L);
        when(repository.findCommentById(1L)).thenReturn(Optional.empty());

        IllegalRequestException exception = assertThrows(IllegalRequestException.class, () -> service.update(dto));
        assertEquals("Comment not found with ID: " + dto.commentId(), exception.getMessage());
    }

    @Test
    void testReject_Success() {
        Comment comment = mock(Comment.class);

        service.reject(comment);

        verify(comment).setStatus(CommentStatus.REJECTED);
        verify(repository).updateComment(comment);
        verify(eventPublisher).publish(any(CommentUpdatedEvent.class));
    }

    @Test
    void testReject_UpdateFailure() {
        Comment comment = mock(Comment.class);

        doThrow(new RuntimeException("Database error")).when(repository).updateComment(comment);

        TaskNotCompletableException exception = assertThrows(TaskNotCompletableException.class, () -> service.reject(comment));
        assertTrue(exception.getMessage().contains("Cannot perform this action right now"));
    }
}