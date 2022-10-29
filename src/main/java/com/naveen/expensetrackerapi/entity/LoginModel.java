package com.naveen.expensetrackerapi.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class LoginModel {

    @NotNull(message = "Email should not be empty")
    @Email(message = "Enter a valid email")
    private String email;

    @NotNull(message = "Password should not be empty")
    private String password;
}
