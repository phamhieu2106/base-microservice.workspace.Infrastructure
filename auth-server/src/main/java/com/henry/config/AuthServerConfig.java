package com.henry.config;

import com.henry.base.BaseObjectLoggAble;
import com.henry.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AuthServerConfig extends BaseObjectLoggAble {

    @Value("${environment.debug}")
    private boolean IS_DEVELOP;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public AuthServerConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable);
        if (IS_DEVELOP) {
            http
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        } else {
            http
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/authenticate/**")
                            .permitAll()
                            .anyRequest().authenticated())
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }
        return http.build();
    }
}
