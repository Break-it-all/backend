package com.goorm.BITA.api.response;

import com.goorm.BITA.common.enums.ResponseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponseDto<T> {
    private ResponseCode code;
    private T data;
    private String message;

    private ApiResponseDto(ResponseCode status, T data, String message) {
        this.code = status;
        this.data = data;
        this.message = message;
    }
    public static <T> ApiResponseDto<T> successResponse(T data) {
        return new ApiResponseDto<>(ResponseCode.SUCCESS, data, null);
    }

    public static ApiResponseDto<?> successWithoutDataResponse() {
        return new ApiResponseDto<>(ResponseCode.SUCCESS, null, null);
    }

    public static ApiResponseDto<?> errorResponse(ResponseCode code) {
        return new ApiResponseDto<>(code, null, code.getMessage());
    }

    public static ApiResponseDto<?> failResponse(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        bindingResult.getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;
                errors.put(fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        });
        return new ApiResponseDto<>(ResponseCode.VALIDATION_ERROR, errors, null);
    }
}
