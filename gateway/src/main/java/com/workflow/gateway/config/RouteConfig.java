package com.workflow.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    private final AuthenticationFilter authFilter;
    public RouteConfig(AuthenticationFilter authFilter) {
        this.authFilter = authFilter;
    }
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        here lb is load balancer
        return builder.routes()
                .route("user-service", r -> r.path("/auth/user/**")
                        .uri("lb://USER-SERVICE"))
                .route("chat-service", r -> r.path("/project/{projectId}/chat/**")
                        //.filters(f -> f.filter(authFilter))
                        .uri("lb://CHAT-SERVICE"))
                .route("chat-service", r -> r.path("/project/chat/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://CHAT-SERVICE"))
                .route("project-service", r -> r.path("/project/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://PROJECT-SERVICE"))
                .route("invite-service", r -> r.path("/invite/accept")
                        .uri("lb://INVITE-SERVICE"))
                .route("invite-service", r -> r.path("/invite/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://INVITE-SERVICE"))
                .build();

//        return builder.routes()
//                .route("user_service", r -> r.path("/auth/user/**")
//                        .uri("http://localhost:8081"))
//                .route("project_service", r -> r.path("/project/**")
//                        .uri("http://localhost:8082"))
//                .build();

    }
}
