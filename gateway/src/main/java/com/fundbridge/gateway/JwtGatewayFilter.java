package com.fundbridge.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class JwtGatewayFilter implements GlobalFilter {

    private final JwtService jwtService;

    public JwtGatewayFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (path.startsWith("/api/users/login") || path.startsWith("/api/users/register")) {
            return chain.filter(exchange);
        }

        String token = extractToken(request);

        if (token == null || !jwtService.validateToken(token)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        UUID userId = jwtService.extractUserId(token);
        String role = jwtService.extractRole(token);
        ServerHttpRequest modifiedRequest = request.mutate()
                .header("X-User-Id", userId.toString())
                .header("X-User-Role","ROLE_" + role.toUpperCase())
                .build();

        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private String extractToken(ServerHttpRequest request) {
        HttpCookie cookie = request.getCookies().getFirst("access_token");
        return cookie != null ? cookie.getValue() : null;
    }
}