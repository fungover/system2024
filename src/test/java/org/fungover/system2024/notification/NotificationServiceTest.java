package org.fungover.system2024.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private JavaMailSender mailSender;

    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        notificationService = new NotificationService();
        notificationService.setMailSender(mailSender);
    }

    @Test
    public void testSendNotification() {
        String username = "testUser";
        String eventType = "testEvent";

        notificationService.sendNotification(username, eventType);
        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    public void testSendNotificationWithException() {
        String username = "testUser";
        String eventType = "testEvent";
        doThrow(new RuntimeException("Mail server error"))
                .when(mailSender).send(any(SimpleMailMessage.class));
        assertThrows(RuntimeException.class, () -> {
            notificationService.sendNotification(username, eventType);
        });
    }
}
