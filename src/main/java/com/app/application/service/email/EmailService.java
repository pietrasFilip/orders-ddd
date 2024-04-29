package com.app.application.service.email;

import com.app.application.dto.email.CreateEmailDto;
import jakarta.mail.MessagingException;

public interface EmailService {
    void send(String email, String subject, String content);
    void sendWithAttachment(CreateEmailDto createEmailDto) throws MessagingException;
}
