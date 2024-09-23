package com.taskgpb.intern.Common.Exceptions;

import lombok.Getter;

@Getter
public class ApiResponseException extends RuntimeException {
    private final int httpStatusCode;

    public ApiResponseException(String message, int httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }
}
