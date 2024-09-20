package com.taskgpb.test.apiVkIntegration;

import com.taskgpb.test.apiVkIntegration.DTOs.ErrorResponse;
import com.taskgpb.test.apiVkIntegration.DTOs.UserInfoResponse;
import com.taskgpb.test.apiVkIntegration.DTOs.VkUserInfoRequest;
import com.taskgpb.test.apiVkIntegration.Exceptions.GroupMembershipNotFoundException;
import com.taskgpb.test.apiVkIntegration.Exceptions.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final VkApiService vkService;

    @GetMapping
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/user-info")
    @Operation(summary = "Получить информацию о пользователе VK",
            description = "Получает ФИО пользователя и проверяет участие в группе VK")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный ответ",
                    content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> getUserInfoFromVkApi(
            @RequestHeader("vk_service_token")
            @Parameter(description = "Сервисный токен VK", required = true) String serviceToken,
            @RequestBody @Valid VkUserInfoRequest request) {
        try {
            UserInfoResponse response = vkService.getUserInfoResponse(request, serviceToken);
            return ResponseEntity.ok(response);
        } catch (UserNotFoundException | GroupMembershipNotFoundException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 404), HttpStatus.NOT_FOUND);
        }
    }
}
