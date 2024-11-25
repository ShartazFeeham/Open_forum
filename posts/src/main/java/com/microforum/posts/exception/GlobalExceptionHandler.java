//package com.microforum.posts.exception;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.Date;
//
//@ControllerAdvice
//public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {
//
//    //private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ce, HttpServletRequest request) {
//        var errorResponse = ErrorResponse.builder()
//                .requestURL(request.getRequestURL().toString())
//                .status(ce.getHttpStatus().toString())
//                .requestURI(request.getRequestURI())
//                .exception(ce.getExceptionName())
//                .timeStamp(new Date())
//                .message(ce.getMessage())
//                .build();
//        //logger.error("Exception occurred! Returning response {}, original exception: {}", errorResponse, ce.toString());
//        return ResponseEntity.status(ce.getHttpStatus()).body(errorResponse);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
//        var errorResponse = ErrorResponse.builder()
//                .requestURL(request.getRequestURL().toString())
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
//                .requestURI(request.getRequestURI())
//                .exception(e.getLocalizedMessage())
//                .timeStamp(new Date())
//                .message(e.getMessage())
//                .build();
////        logger.error("Exception occurred! Returning response {}, original exception: {}", errorResponse, e.toString());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//    }
//
//}