package com.taskgpb.intern.Common.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VkUserInfoRequest {

    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    private long userId;

    @NotNull(message = "Group ID cannot be null")
    @JsonProperty("group_id")
    private long groupId;
}
