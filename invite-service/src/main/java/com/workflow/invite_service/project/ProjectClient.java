package com.workflow.invite_service.project;


import com.workflow.invite_service.invite.dto.ResponseMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PROJECT-SERVICE")
public interface ProjectClient {
    @PostMapping("project/{projectId}/add/user")
    ResponseMessage addUser(@PathVariable Long projectId, @RequestParam Long userId);
}
