package com.workflow.invite_service.invite;

import com.workflow.invite_service.invite.dto.ResponseMessage;
import com.workflow.invite_service.mail.EmailServiceImpl;
import com.workflow.invite_service.project.ProjectClient;
import com.workflow.invite_service.user.User;
import com.workflow.invite_service.user.UserClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final EmailServiceImpl emailServiceImpl;
    private final ProjectClient projectClient;
    private final UserClient userClient;

    public InvitationServiceImpl(InvitationRepository invitationRepository, EmailServiceImpl emailServiceImpl, ProjectClient projectClient, UserClient userClient) {
        this.invitationRepository = invitationRepository;
        this.emailServiceImpl = emailServiceImpl;
        this.projectClient = projectClient;
        this.userClient = userClient;
    }

    @Override
    public void sendInvitation(Long projectId, String email) {
        try{
            String invitationToken = UUID.randomUUID().toString();
            Invitation invitation = new Invitation();
            invitation.setEmail(email);
            invitation.setProjectId(projectId);
            invitation.setToken(invitationToken);
            invitationRepository.save(invitation);

            String invitationLink = "http://localhost:3000/accept_invitation?token=" + invitationToken;

            emailServiceImpl.sendEmailWithToken(email, invitationLink);
        }
        catch(Exception e){
            throw new InternalError(e.getMessage());
        }
    }

    @Override
    public ResponseMessage acceptInvitation(String token) throws Exception {
        Invitation invitation = invitationRepository.findByToken(token);
        if(invitation == null) {
            throw new Exception("Invalid invitation token");
        }
//        update project service
        User user = userClient.getUserByUsername(invitation.getEmail());

        ResponseMessage response = projectClient.addUser(invitation.getProjectId(), user.getId());

        return response;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation invitation = invitationRepository.findByEmail(userEmail);
        return invitation.getToken();
    }

}
