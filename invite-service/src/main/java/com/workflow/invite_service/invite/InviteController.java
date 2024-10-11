package com.workflow.invite_service.invite;

import com.workflow.invite_service.invite.dto.RequestDTO;
import com.workflow.invite_service.invite.dto.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invite")
public class InviteController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final InvitationServiceImpl invitationService;

    public InviteController(InvitationServiceImpl invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping("hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("project/{projectId}")
    public ResponseEntity<ResponseMessage> sendInvite(@PathVariable Long projectId, @RequestBody RequestDTO requestDTO) {
        try{
            log.info(requestDTO.toString());
            invitationService.sendInvitation(projectId, requestDTO.getEmail());
            return ResponseEntity.ok(new ResponseMessage("invitation sent successfully"));
        }
        catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("accept")
    public ResponseEntity<?> acceptInvitation(@RequestParam String token) {
        try{
            ResponseMessage response = invitationService.acceptInvitation(token);
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
