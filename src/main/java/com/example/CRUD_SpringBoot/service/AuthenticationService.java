package com.example.CRUD_SpringBoot.service;

import com.example.CRUD_SpringBoot.dto.request.AccountLoginRequest;
import com.example.CRUD_SpringBoot.dto.request.AccountRegisterRequest;
import com.example.CRUD_SpringBoot.dto.request.IntrospectTokenRequest;
import com.example.CRUD_SpringBoot.dto.response.IntrospectTokenResponse;
import com.example.CRUD_SpringBoot.dto.response.LoginResponse;
import com.example.CRUD_SpringBoot.dto.response.RegisterResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
    RegisterResponse registerService(AccountRegisterRequest request);
    LoginResponse loginService(AccountLoginRequest request);
    IntrospectTokenResponse introspectToken(IntrospectTokenRequest request) throws JOSEException, ParseException;
}
