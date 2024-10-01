package com.example.CRUD_SpringBoot.service.impl;

import com.example.CRUD_SpringBoot.dto.request.AccountLoginRequest;
import com.example.CRUD_SpringBoot.dto.request.AccountRegisterRequest;
import com.example.CRUD_SpringBoot.dto.request.IntrospectTokenRequest;
import com.example.CRUD_SpringBoot.dto.response.IntrospectTokenResponse;
import com.example.CRUD_SpringBoot.dto.response.LoginResponse;
import com.example.CRUD_SpringBoot.dto.response.RegisterResponse;
import com.example.CRUD_SpringBoot.dto.response.TokenResponse;
import com.example.CRUD_SpringBoot.entity.Account;
import com.example.CRUD_SpringBoot.enums.Role;
import com.example.CRUD_SpringBoot.exception.AppException;
import com.example.CRUD_SpringBoot.exception.ErrorCode;
import com.example.CRUD_SpringBoot.jwt.generateAllToken;
import com.example.CRUD_SpringBoot.mapper.AccountMapper;
import com.example.CRUD_SpringBoot.repository.AccountRepository;
import com.example.CRUD_SpringBoot.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Service
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
     AccountRepository accountRepository;
    @Autowired
     AccountMapper accountMapper;
    @Autowired
     generateAllToken generateAllToken;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse registerService(AccountRegisterRequest request) {
        if (accountRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        try {
            String encodedPassword = passwordEncoder.encode(request.getPassWord());
            Account account =accountMapper.toAccount(request);
            account.setPassWord(encodedPassword);
            //            role
//            HashSet<String> roles = new HashSet<>();
//            roles.add(Role.USER.name());
            account.setRole(Role.USER.name());

            accountRepository.save(account);
            return RegisterResponse.builder()
                    .message("Registration is successful, log in with email and password.")
                    .authenticated(true)
                    .build();
        } catch (Exception e) {
            return RegisterResponse.builder()
                    .message(e.getMessage())
                    .authenticated(true)
                    .build();
        }
    }

    @Override
    public LoginResponse loginService(AccountLoginRequest request) {
        Account account =accountRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AppException((ErrorCode.USER_NOT_EXISTED)));
//        check passWord
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(!passwordEncoder.matches(request.getPassWord(),
                account.getPassWord()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
//            return LoginResponse.builder()
//                    .message("Incorrect password")
//                    .build();
//         create accessToken & refreshToken
        TokenResponse accessToken = generateAllToken.generateAccessToken(account.getId(), account.getRole());
        String refreshToken = generateAllToken.generateRefreshTokenToken(account.getId());
        // Save refreshToken
        account.setRefreshToken(refreshToken);
        accountRepository.save(account);
        return LoginResponse.builder()
                .message("Login is successfully")
                .email(account.getEmail())
                .userName(account.getUserName())
                .accessToken(accessToken.getAccessToken())
                .expirationTime(accessToken.getExpirationTime())
                .role(account.getRole())
                .build();
    }

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;
    @Override
    public IntrospectTokenResponse introspectToken(IntrospectTokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectTokenResponse.builder()
                .valid(verified && expityTime.after(new Date()))
                .build();
    }
}
