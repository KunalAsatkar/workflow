package com.workflow.project_service.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("USER-SERVICE")
public interface UserClient {

    @GetMapping("auth/user/{id}")
    User getUserById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader);
}
