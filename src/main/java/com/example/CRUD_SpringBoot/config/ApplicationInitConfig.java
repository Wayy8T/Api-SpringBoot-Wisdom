package com.example.CRUD_SpringBoot.config;

import com.example.CRUD_SpringBoot.entity.Account;
import com.example.CRUD_SpringBoot.enums.Role;
import com.example.CRUD_SpringBoot.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AllArgsConstructor
@Data
@Slf4j
public class ApplicationInitConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository) {
        return args -> {
            if(accountRepository.findByUserName("admin").isEmpty()){
                Account account = Account.builder()
                        .userName("admin")
                        .email("admin@admin.com")
                        .role(Role.ADMIN.name())
                        .passWord(passwordEncoder.encode("admin"))
                        .build();
                accountRepository.save(account);
                log.warn("Account admin created");
            }
        };
    }
}
