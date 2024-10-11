package com.workflow.project_service.chat;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class Chat {
    private Long id;
    private Long projectId;
}