package com.example.CRUD_SpringBoot.controller;

import com.example.CRUD_SpringBoot.dto.request.AccountLoginRequest;
import com.example.CRUD_SpringBoot.dto.request.AccountRegisterRequest;
import com.example.CRUD_SpringBoot.dto.request.IntrospectTokenRequest;
import com.example.CRUD_SpringBoot.dto.response.ApiResponse;
import com.example.CRUD_SpringBoot.dto.response.IntrospectTokenResponse;
import com.example.CRUD_SpringBoot.dto.response.LoginResponse;
import com.example.CRUD_SpringBoot.dto.response.RegisterResponse;
import com.example.CRUD_SpringBoot.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/auth")
@Data
@CrossOrigin(origins = "http://localhost:5173") // Cho phép yêu cầu từ nguồn này

public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    ApiResponse<RegisterResponse> registerControllor(@RequestBody AccountRegisterRequest request) {
        var result = authenticationService.registerService(request);
        return ApiResponse.<RegisterResponse>builder()
                .result(result)
                .build();
    }

    @CrossOrigin(origins = "http://localhost:5173") // Cấu hình để cho phép từ frontend
    @PostMapping("/log-in")
    ApiResponse<LoginResponse> loginControllor(@RequestBody AccountLoginRequest request) {
        var result = authenticationService.loginService(request);
        return ApiResponse.<LoginResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/verify-token")
    ApiResponse<IntrospectTokenResponse> introspectToken(@RequestBody IntrospectTokenRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspectToken(request);
        return ApiResponse.<IntrospectTokenResponse>builder()
                .result(result)
                .build();
    }
}
