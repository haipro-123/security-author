package com.example.demo.dto.response;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ErrorResponse {
    private Date timestamp;
    private String path;
    private String message;
}
