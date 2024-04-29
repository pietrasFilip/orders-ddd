package com.app.application.service.email;

import com.app.application.dto.email.CreateEmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SendWithAttachmentTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailServiceImpl emailService;

    @Test
    @DisplayName("When method is invoked exactly one time")
    void test1() throws MessagingException {
        var createEmailDto = new CreateEmailDto("test", "test", "test", "test");
        var mimeMessage = mock(MimeMessage.class);

        when(javaMailSender.createMimeMessage())
                .thenReturn(mimeMessage);

        emailService.sendWithAttachment(createEmailDto);
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }
}
