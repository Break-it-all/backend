package com.goorm.BITA.api.exception;

import com.goorm.BITA.common.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {
    private final ResponseCode responseCode;
    public BaseException(ResponseCode code, String message) {
        super(message);
        this.responseCode = code;
    }
}
