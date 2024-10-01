package com.example.CRUD_SpringBoot.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchConnectionDetails;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
     UUID id;
    @Column(name = "email", nullable = false, length = 170)
     String email;
    @Column(name = "userName", nullable = false, length = 170)
     String userName;
    @Column(name = "passWord", nullable = false, length = 256)
     String passWord;
    @Column(name = "refreshToken", length = 1000)
     String refreshToken = null; // Mặc định là null
    @Column(name = "role",  length = 256)
     String role = "user"; // Mặc định là "user"
}
