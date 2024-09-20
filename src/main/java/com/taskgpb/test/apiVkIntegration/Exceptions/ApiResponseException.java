package com.taskgpb.test.apiVkIntegration.Exceptions;

import lombok.Getter;

@Getter
public class ApiResponseException extends RuntimeException {
    private final int httpStatusCode;

    public ApiResponseException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
