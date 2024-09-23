package com.taskgpb.test.Common.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("message")
    private String message;

    @JsonProperty("error_code")
    private int errorCode;

    public ErrorResponse(String message, int errorCode) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
    }
}
