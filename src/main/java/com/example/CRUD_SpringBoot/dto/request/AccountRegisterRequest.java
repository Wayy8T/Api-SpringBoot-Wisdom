package com.example.CRUD_SpringBoot.dto.request;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class AccountRegisterRequest {
     UUID id;
     String email;
     String userName;
     String passWord;
     String role;
     String refreshToken;
}
