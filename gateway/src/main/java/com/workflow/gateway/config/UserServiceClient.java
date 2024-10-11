package com.workflow.gateway.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserServiceClient {

    private final WebClient.Builder webClientBuilder;

    public UserServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Boolean> validateToken(String token) {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/auth/user/validate")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}