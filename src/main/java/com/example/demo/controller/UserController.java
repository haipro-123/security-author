package com.example.demo.controller;

import com.example.demo.dto.request.CheckOtpRequest;
import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.request.RegisterRequest;
import com.example.demo.service.IUserService;
import com.example.demo.util.Constant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private IUserService userService;
    @PostMapping(Constant.URL_LOGIN)
    public ResponseEntity<?> login(@Valid @NotNull @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.auth(request.getUsername(),request.getPassword()));
    }
    @PostMapping(Constant.URL_CHECK_OTP)
    public ResponseEntity<?> checkOtp(@Valid @NotNull @RequestBody CheckOtpRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkOtp(request.getIdUser(),request.getOtp()));
    }
    @PostMapping(Constant.URL_REGISTER)
    public ResponseEntity<?> register(@Valid @NotNull @RequestBody RegisterRequest request){
        userService.add(request.getUsername(),request.getPassword());
        return new ResponseEntity<>(HttpStatus.CREATED) ;
    }

}
