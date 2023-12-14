package com.goorm.BITA.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS(2000, "success"),
    BAD_ERROR(4000, "bad error"),
    VALIDATION_ERROR(4001, "Validation Error");

    private final int code;
    private final String message;
}