package com.base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class ResourceConfig {

    @Value("${base.environment.debug:false}")
    private boolean DEBUG_ENVIRONMENT;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        if (!DEBUG_ENVIRONMENT) {
            http.authorizeExchange(exchanges -> exchanges
                    .pathMatchers("/auth-server/**").permitAll()
                    .anyExchange().authenticated()
            );
        }

        return http.build();
    }
}
