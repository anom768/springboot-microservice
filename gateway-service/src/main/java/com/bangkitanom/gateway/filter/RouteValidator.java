package com.bangkitanom.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> apiEndpoints = List.of(
            "/api/auth/register", "/api/auth/generate-token", "/api/auth/validate-token",
            "/eureka/**"
    );

    public Predicate<ServerHttpRequest> isSecure = request -> apiEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
