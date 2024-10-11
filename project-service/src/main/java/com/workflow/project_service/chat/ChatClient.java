package com.workflow.project_service.chat;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient("CHAT-SERVICE")
public interface ChatClient {
    @PostMapping("project/{projectId}/chat/create")
    Chat createChat(@PathVariable Long projectId);
}
