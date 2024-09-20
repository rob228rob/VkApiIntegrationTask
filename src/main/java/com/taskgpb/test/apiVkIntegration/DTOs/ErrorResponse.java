package com.taskgpb.test.apiVkIntegration.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
