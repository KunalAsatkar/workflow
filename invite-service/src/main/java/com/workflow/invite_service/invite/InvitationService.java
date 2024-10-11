package com.workflow.invite_service.invite;

import com.workflow.invite_service.invite.dto.ResponseMessage;

public interface InvitationService {

    void sendInvitation(Long projectId, String email );

    ResponseMessage acceptInvitation(String token) throws Exception;

    String getTokenByUserMail(String userEmail);

}
