package com.workflow.chat_service.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("project/{projectId}/chat")
public class ChatController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ChatServiceImpl chatService;

    public ChatController(ChatServiceImpl chatService) {
        this.chatService = chatService;
    }

    @GetMapping("hello")
    ResponseEntity<String> hello(@PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok("Hello from chats " + projectId);
    }

    @PostMapping("create")
    public ResponseEntity<ChatDTO> createChat(@PathVariable Long projectId) {
        try{
            ChatDTO chatDTO = chatService.createChat(projectId);
            return ResponseEntity.ok(chatDTO);
        }
        catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
