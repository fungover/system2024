package org.fungover.system2024.notification;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;

    @Value("${notification.email.recipient}")
    private String recipientEmail;

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendNotification(String username, String eventType){
        try {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Notification");
        message.setText("Hello,\n\n" + username + " has successfully completed their " +
                eventType + ".\n\n" + "Please review their profile at your earliest convenience.\n\n" +
                "Best regards,\n" + "System2024");
        mailSender.send(message);
            logger.info("Notification: {} has completed {}", username, eventType);
        } catch (MailException e) {
            logger.error("Failed to send notification for {}: {}", username, eventType, e);
        }
    }
}
