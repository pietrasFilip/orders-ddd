package com.app.application.service.email;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SendTest {

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailServiceImpl emailService;

    @Test
    @DisplayName("When method is invoked exactly one time")
    void test1() {
        emailService.send("test", "test", "test");
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
