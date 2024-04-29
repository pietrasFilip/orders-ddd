package com.app.infrastructure.api.events.listener;

import com.app.application.dto.email.CreateEmailDto;
import com.app.application.service.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailWithAttachmentListener {
    private final EmailService emailService;
    private static final Logger logger = LogManager.getRootLogger();

    @Async
    @EventListener
    public void sendWithAttachment(CreateEmailDto createEmailDto) throws MessagingException {
        logger.info("Sending mail with attachment to {}...", createEmailDto.email());
        emailService.sendWithAttachment(createEmailDto);

        logger.info("Email sent.");
    }
}
