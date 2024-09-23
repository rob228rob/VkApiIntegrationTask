package com.taskgpb.test.Common.SecurityPackage.Auth;

import com.taskgpb.test.Common.CustomException.UserBadCredentialsException;
import com.taskgpb.test.Common.DTOs.LoginDTO;
import com.taskgpb.test.Common.DTOs.RegisterDTO;
import com.taskgpb.test.Common.SecurityPackage.CustomUser;
import com.taskgpb.test.Common.SecurityPackage.CustomUserDetailsService;
import com.taskgpb.test.Common.DTOs.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "API для аутентификации и регистрации пользователей")
public class AuthController {

    private final CustomUserDetailsService userService;

    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Регистрация нового пользователя", description = "Создает нового пользователя в системе. Требуются логин, пароль и подтверждение пароля.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomUser.class))),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким логином уже существует",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Неверные данные пользователя",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerTeacher(
            @RequestBody @Valid RegisterDTO registerTeacherDTO) {
        if (userService.existByUsername(registerTeacherDTO.getLogin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this login already exists");
        }

        try {
            CustomUser newTeacher = userService.saveUser(registerTeacherDTO);
            return ResponseEntity.ok(newTeacher);
        } catch (UserBadCredentialsException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Авторизация пользователя", description = "Авторизация пользователя с использованием логина и пароля.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Неверный логин или пароль",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var user = userService.findUserByUsername(loginRequest.getUsername());

            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with this username does not exist: " + loginRequest.getUsername());
            }

            return ResponseEntity.ok().build();
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
