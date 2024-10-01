package com.example.CRUD_SpringBoot.jwt;

import com.example.CRUD_SpringBoot.dto.response.TokenResponse;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
public class generateAllToken {
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public TokenResponse generateAccessToken(UUID idAcc, String role) {
        // Tạo header
        JWSHeader header  = new JWSHeader(JWSAlgorithm.HS512);

        // Thiết lập thời gian hết hạn (7 ngày)
        long expirationTimeInMillis = Instant.now().plus(7, ChronoUnit.DAYS).toEpochMilli();
        Date expirationDate = new Date(expirationTimeInMillis);

        // Tạo payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(idAcc.toString())
                .issuer("devIT.com")
                .issueTime(new Date())  // Thời điểm phát hành
                .expirationTime(expirationDate)  // Thời điểm hết hạn
                .claim("scope", role)
                .build();

        // Convert sang JSON
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        // Sign token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            String token = jwsObject.serialize();
            // Trả về đối tượng TokenResponse chứa token và thời gian hết hạn
            return new TokenResponse(token, expirationTimeInMillis);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateRefreshTokenToken(UUID idAcc){
        // tạo header
        // header có ND thuật toan mà ta sử dụng
        JWSHeader header  = new JWSHeader(JWSAlgorithm.HS512);
        // tạo payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(idAcc.toString())
                .issuer("devIT.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(15, ChronoUnit.DAYS).toEpochMilli()
                ))
                .claim("customClaim", "Custom")
                .build();
        // cv ve json
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payload);
        // sign token
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
