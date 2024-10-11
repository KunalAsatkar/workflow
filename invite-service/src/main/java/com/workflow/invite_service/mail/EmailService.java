package com.workflow.invite_service.mail;

public interface EmailService {
    public void sendEmailWithToken(String userEmail, String token);

}
