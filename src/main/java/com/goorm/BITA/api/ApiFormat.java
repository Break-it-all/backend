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
public class ApiFormat<T> {
    private String status; // 추후에 enum으로 변경
    private T data;
    private String message;

    private ApiFormat(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
    public static <T> ApiFormat<T> successResponse(T data) {
        // TODO: status를 enum으로 변경
        return new ApiFormat<>("success", data, null);
    }

    public static ApiFormat<?> successWithoutDataResponse() {
        // TODO: status를 enum으로 변경
        return new ApiFormat<>("success", null, null);
    }

    public static ApiFormat<?> errorResponse(String message) {
        // TODO: status를 enum으로 변경
        return new ApiFormat<>("error", null, message);
    }

    public static ApiFormat<?> failResponse(BindingResult bindingResult) {
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
        return new ApiFormat<>("fail", errors, null);
    }
}
