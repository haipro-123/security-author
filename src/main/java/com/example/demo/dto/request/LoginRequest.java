package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "username is null or empty")
    private String username;
    @NotBlank(message ="password is null or empty")
    private String password;
}
