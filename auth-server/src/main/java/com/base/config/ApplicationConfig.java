package com.base.config;

import com.base.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${base.jwt.secret-key:}")
    private String base64SecretKey;
    @Value("${base.jwt.expiration:}")
    private long expiration;

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils(base64SecretKey, expiration);
    }
}
