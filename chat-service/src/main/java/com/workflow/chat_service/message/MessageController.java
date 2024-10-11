package com.workflow.chat_service.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/{projectId}/chat/message")
public class MessageController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("hello")
    public String hello() {
        return "Hello World";
    }


    @GetMapping()
    public ResponseEntity<List<Message>> chat(@PathVariable("projectId") Long projectId) {
        try{
            List<Message> message = messageService.getMessagesByProjectId(projectId);
            return ResponseEntity.ok(message);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
