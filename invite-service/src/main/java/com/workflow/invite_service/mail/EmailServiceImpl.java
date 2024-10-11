package com.workflow.invite_service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MailSendException {
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String subject = "Invitation to Join Project Team";
            String text = "Click the link to join the project Team " + link;

            helper.setSubject(subject);
            helper.setText(text, true);
            helper.setTo(userEmail);
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e) {
            throw new MailSendException(e.getMessage());
        }


    }
}
