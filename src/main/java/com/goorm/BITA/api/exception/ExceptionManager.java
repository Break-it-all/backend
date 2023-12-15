package com.goorm.BITA.api.exception;

import com.goorm.BITA.api.response.ApiResponseDto;
import com.goorm.BITA.common.enums.ResponseCode;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(RuntimeException.class)
    public ApiResponseDto<?> handleRuntimeException(RuntimeException e) {
        return ApiResponseDto.exceptionResponse(ResponseCode.BAD_ERROR, e.getMessage());
    }
    @ExceptionHandler(BaseException.class)
    public ApiResponseDto<?> handleBaseException(BaseException e) {
        return ApiResponseDto.exceptionResponse(e.getResponseCode(), e.getMessage());
    }
}
