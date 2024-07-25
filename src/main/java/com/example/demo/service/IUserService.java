package com.example.demo.service;

import com.example.demo.dto.response.CheckOtpResponse;
import com.example.demo.dto.response.LoginResponse;

public interface IUserService {
    LoginResponse auth(String username, String password);

    CheckOtpResponse checkOtp(String idUser, String otp);

    void add(String username, String password);
}
