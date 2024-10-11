package com.workflow.chat_service.chat;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class ChatDTO {
    private Long id;
    private Long projectId;
}
