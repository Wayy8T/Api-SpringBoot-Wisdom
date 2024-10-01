package com.example.CRUD_SpringBoot.config;

import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @NonFinal
    @Value("${jwt.signerKey}")
    private String signerKey;

    private String[] PUBLIC_URLS = {"/auth/register", "/auth/log-in", "/auth/verify-token"};

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Cho phép tất cả các URL
                        .allowedOrigins("http://localhost:5173") // Cho phép từ một domain cụ thể, ví dụ: React App ở localhost
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Các phương thức HTTP được phép
                        .allowedHeaders("*") // Cho phép tất cả headers
                        .allowCredentials(true) // Cho phép gửi thông tin xác thực (cookies, headers)
                        .exposedHeaders("Authorization"); // Nếu bạn cần expose headers cụ thể
            }
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, PUBLIC_URLS).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/categories/all-category","api/products/all", "api/products/**","api/categories/detail-category/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "api/categories/edit-category/**").permitAll()

//                        .requestMatchers(HttpMethod.GET, "api/categories","api/products/all").hasAnyAuthority("SCOPE_ADMIN", "SCOPE_USER")
//                        .requestMatchers(HttpMethod.PUT,"api/products/").hasAnyAuthority("SCOPE_ADMIN")
//                        .requestMatchers(HttpMethod.POST,"api/products/create", "api/categories/create-or-update", "api/categories/create-or-update/").hasAnyAuthority("SCOPE_ADMIN")
                        .anyRequest().authenticated()
                )
//                verify token true moi cho thuc hien
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
