package com.base.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    @Value("${base.jwt.secret}")
    private String JWT_SECRET;

    @Value("${base.permit.uri:}")
    private String PERMIT_URI;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        boolean isPermitUri = Arrays.stream(PERMIT_URI.split(","))
                .anyMatch(path::startsWith);
        if (isPermitUri) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String jwt = authHeader.substring(7);

        // TODO: Validate JWT. Có thể sử dụng thư viện JWT như io.jsonwebtoken hoặc java-jwt
        if (!isValidJwt(jwt)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Có thể inject thêm info user vào header nếu cần
        // exchange.getRequest().mutate().header("user-id", ...);

        return chain.filter(exchange);
    }

    private boolean isValidJwt(String jwt) {
        // TODO: Validate JWT logic ở đây (giải mã, check chữ ký, check hết hạn)
        // Để demo, tạm luôn true
        return true;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
