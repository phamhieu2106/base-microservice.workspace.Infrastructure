package com.base.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.base.base.domain.response.WrapResponse;
import com.base.service.UserDetailsServiceConfig;
import com.base.util.JwtUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationWebFilter implements WebFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceConfig userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public @NonNull Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String token = getTokenFromRequest(request);

        if (token == null) {
            exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
            Object wrapResponse = WrapResponse.response(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            byte[] wrapResponseBytes = objectMapper.writeValueAsString(wrapResponse).getBytes();
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(wrapResponseBytes)));
        }

        String username = jwtUtil.extractUsername(token);
        return Mono.fromCallable(() -> userDetailsService.loadUserByUsername(username))
                .flatMap(userDetails -> {
                    if (jwtUtil.isInvalidToken(token)) {
                        ServerHttpRequest modifiedRequest = request.mutate()
                                .header("X-User-Name", username)
                                .build();
                        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                    }
                    return chain.filter(exchange.mutate().request(request).build());
                });

    }

    private String getTokenFromRequest(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
