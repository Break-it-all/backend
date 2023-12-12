package com.goorm.BITA.api;

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
    private String status; // 추후에 enum으로 변경
    private T data;
    private String message;

    private ApiResponseDto(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
    public static <T> ApiResponseDto<T> successResponse(T data) {
        // TODO: status를 enum으로 변경
        return new ApiResponseDto<>("success", data, null);
    }

    public static ApiResponseDto<?> successWithoutDataResponse() {
        // TODO: status를 enum으로 변경
        return new ApiResponseDto<>("success", null, null);
    }

    public static ApiResponseDto<?> errorResponse(String message) {
        // TODO: status를 enum으로 변경
        return new ApiResponseDto<>("error", null, message);
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
        // TODO: status를 enum으로 변경
        return new ApiResponseDto<>("fail", errors, null);
    }
}
