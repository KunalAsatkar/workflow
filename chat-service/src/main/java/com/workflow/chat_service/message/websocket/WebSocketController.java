package com.workflow.chat_service.message.websocket;

import com.workflow.chat_service.message.Message;
import com.workflow.chat_service.message.MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final MessageServiceImpl messageService;
    private final SimpMessagingTemplate messagingTemplate;


    public WebSocketController(MessageServiceImpl messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/{projectId}/chat")
    public void handleMessage(@Payload Message message, @DestinationVariable Long projectId) {
        try {
            Message savedMessage = messageService.sendMessage(message, projectId);
            // Broadcast the saved message to all subscribers
            messagingTemplate.convertAndSend("/chat/messages", savedMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
            messagingTemplate.convertAndSend("/chat/messages", e.getMessage());
        }
    }
}