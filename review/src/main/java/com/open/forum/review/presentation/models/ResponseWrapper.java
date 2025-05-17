package com.open.forum.review.presentation.models;

import com.open.forum.review.shared.utility.ServerZonedDateTime;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.function.Supplier;

@Component
public class ResponseWrapper {

    public static <R> ResponseEntity<BaseResponseModel<R>> result(Supplier<R> supplier,
                                                                  HttpStatus httpStatus) {
        final BaseResponseModel<R> baseResponseModel = new BaseResponseModel<>();
        baseResponseModel.setRequestedAt(ServerZonedDateTime.now());
        final R result = getResult(supplier);
        final BaseResponseModel<R> response = finalizeBaseModel(baseResponseModel, result, httpStatus);
        return ResponseEntity.status(httpStatus).body(response);
    }

    private static <R> BaseResponseModel<R> finalizeBaseModel(BaseResponseModel<R> baseResponseModel,
                                                              R result, HttpStatus httpStatus) {
        baseResponseModel.setData(result);
        baseResponseModel.setStatus(httpStatus);
        baseResponseModel.setRespondedAt(ServerZonedDateTime.now());
        baseResponseModel.setResponseTimeInMillis(baseResponseModel.getRespondedAt().toInstant().toEpochMilli()
                - baseResponseModel.getRequestedAt().toInstant().toEpochMilli());
        baseResponseModel.setTraceId(MDC.get("traceId"));
        baseResponseModel.setPath(getCurrentRequest().getRequestURI());
        return baseResponseModel;
    }

    private static <R> R getResult(Supplier<R> supplier) {
        return supplier.get();
    }

    private static HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalStateException("No current HTTP request available");
        }
        return attributes.getRequest();
    }
}