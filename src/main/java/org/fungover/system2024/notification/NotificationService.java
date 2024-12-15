package org.fungover.system2024.notification;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Setter
    @Autowired
    private JavaMailSender mailSender;

    @Value("${notification.email.recipient}")
    private String recipientEmail;

    public void sendNotification(String username, String eventType){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Notification");
        message.setText("Hello,\n\n" + username + " has successfully completed their " +
                eventType + ".\n\n" + "Please review their profile at your earliest convenience.\n\n" +
                "Best regards,\n" + "System2024");
        mailSender.send(message);
        System.out.println("Notification: " + username + " has completed " + eventType);
    }
}
