package com.example.CRUD_SpringBoot.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
     String message;
     String userName;
     String accessToken;
     String email;
     String role;
     long expirationTime;
}
