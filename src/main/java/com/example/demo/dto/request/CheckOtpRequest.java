package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckOtpRequest {
    @NotBlank(message = "id user is null or empty")
    private String idUser;
    @NotBlank(message = "otp is null or empty")
    private String otp;
}
