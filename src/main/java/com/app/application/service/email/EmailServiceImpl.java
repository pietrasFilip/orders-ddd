package com.app.application.service.email;

import com.app.application.dto.email.CreateEmailDto;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Value("${pdf.path}")
    private String pdfFilePath;

    private final JavaMailSender javaMailSender;

    @Override
    public void send(String email, String subject, String content) {
        var simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendWithAttachment(CreateEmailDto createEmailDto) throws MessagingException {
        var mimeMessage = javaMailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(createEmailDto.email());
        helper.setSubject(createEmailDto.subject());
        helper.setText(createEmailDto.emailContent());

        var file = new FileSystemResource(new File(pdfFilePath + createEmailDto.filename() + ".pdf"));
        helper.addAttachment(createEmailDto.filename() + ".pdf", file);
        javaMailSender.send(mimeMessage);
    }
}
