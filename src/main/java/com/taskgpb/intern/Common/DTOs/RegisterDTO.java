package com.taskgpb.intern.Common.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taskgpb.intern.Common.CustomException.UserBadCredentialsException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.coyote.BadRequestException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotNull
    private String login;

    @NotNull
    private String password;

    @NotNull
    private String confirmPassword;

    public void validate() throws BadRequestException {
        this
                .validatePassword()
                .validateConfirmPassword()
                .validateLogin();
    }

    private RegisterDTO validateLogin() throws BadRequestException {
        if (this.login.length() < 3) {
            throw new BadRequestException("Login length must be 3 or more characters");
        }

        return this;
    }

    private RegisterDTO validateConfirmPassword() throws BadRequestException {
        if (!this.confirmPassword.equals(this.password)) {
            throw new BadRequestException("Confirm password must be equals to password");
        }

        if (this.confirmPassword.length() < 8) {
            throw new BadRequestException("Confirm password must be at least 8 characters");
        }

        return this;
    }

    private RegisterDTO validatePassword() throws BadRequestException {
        if (this.password.length() < 8) {
            throw new BadRequestException("Password must be at least 8 characters");
        }

        return this;
    }
}
