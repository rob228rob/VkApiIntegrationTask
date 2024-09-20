package com.taskgpb.test.apiVkIntegration.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserInfoResponse {
    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("member")
    private boolean member;
}
