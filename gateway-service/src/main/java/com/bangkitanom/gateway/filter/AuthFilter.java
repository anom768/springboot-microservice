package com.bangkitanom.gateway.filter;

import com.bangkitanom.gateway.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;
//    private RestTemplate restTemplate;

    public AuthFilter() {
        super(Config.class);
    }

    public static class Config{}

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (routeValidator.isSecure.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                List<String> authHeaderValue = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
                if (authHeaderValue != null && !authHeaderValue.isEmpty()) {
                    String token = authHeaderValue.get(0);
                    if (token != null && token.startsWith("Bearer ")) {
                        token = token.substring(7);
                    }

                    try {
                        //restTemplate.getForObject("http://localhost:8099/api/auth/validate-token?token="+token, String.class);
                        jwtUtil.validateToken(token);
                    } catch (Exception e) {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                } else {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            }
            return chain.filter(exchange);
        };
    }

}
