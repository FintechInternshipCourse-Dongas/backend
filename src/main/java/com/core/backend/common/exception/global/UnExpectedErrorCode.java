package com.core.backend.common.exception.global;

import com.core.backend.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UnExpectedErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "inertnal_server_error");

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;
}
