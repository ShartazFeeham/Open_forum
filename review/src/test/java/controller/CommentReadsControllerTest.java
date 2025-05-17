//package controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.open.forum.review.application.dto.comment.CommentReadDTO;
//import com.open.forum.review.application.service.ports.CommentServiceReads;
//import com.open.forum.review.presentation.controllers.comment.CommentReadsController;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(CommentReadsController.class)
//class CommentReadsControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private CommentServiceReads commentServiceReads;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        Mockito.reset(commentServiceReads);
//    }
//
//    @Test
//    void readComment_Success() throws Exception {
//        // Arrange
//        Long commentId = 1L;
//        CommentReadDTO mockComment = CommentReadDTO.builder()
//                .commentId(commentId)
//                .contents(List.of())
//                .build();
//        when(commentServiceReads.read(commentId)).thenReturn(mockComment);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/v1/comments/{commentId}", commentId)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.commentId").value(commentId));
//        verify(commentServiceReads, times(1)).read(commentId);
//    }
//
//    @Test
//    void readComment_NotFound() throws Exception {
//        // Arrange
//        Long commentId = 1L;
//        when(commentServiceReads.read(commentId)).thenThrow(new RuntimeException("Comment not found"));
//
//        // Act & Assert
//        mockMvc.perform(get("/api/v1/comments/{commentId}", commentId)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Comment not found"));
//        verify(commentServiceReads, times(1)).read(commentId);
//    }
//
//    @Test
//    void readCommentsByUser_Success() throws Exception {
//        // Arrange
//        Long userId = 1L;
//        int page = 0;
//        int size = 10;
//        List<CommentReadDTO> mockComments = List.of(
//                CommentReadDTO.builder().commentId(1L).contents(List.of()).build(),
//                CommentReadDTO.builder().commentId(2L).contents(List.of()).build()
//        );
//        when(commentServiceReads.readByUser(userId, page, size)).thenReturn(mockComments);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/v1/comments/user/{userId}", userId)
//                .param("page", String.valueOf(page))
//                .param("size", String.valueOf(size))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(2));
//        verify(commentServiceReads, times(1)).readByUser(userId, page, size);
//    }
//
//    @Test
//    void readCommentsByPost_Success() throws Exception {
//        // Arrange
//        Long postId = 1L;
//        int page = 0;
//        int size = 10;
//        List<CommentReadDTO> mockComments = List.of(
//                CommentReadDTO.builder().commentId(1L).contents(List.of()).build(),
//                CommentReadDTO.builder().commentId(2L).contents(List.of()).build()
//        );
//        when(commentServiceReads.read(postId, page, size)).thenReturn(mockComments);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/v1/comments/post/{postId}", postId)
//                .param("page", String.valueOf(page))
//                .param("size", String.valueOf(size))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.length()").value(2));
//        verify(commentServiceReads, times(1)).read(postId, page, size);
//    }
//}