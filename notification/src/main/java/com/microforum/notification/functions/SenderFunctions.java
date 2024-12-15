package com.microforum.notification.functions;

import com.microforum.notification.models.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class SenderFunctions {

    private static final Logger logger = LoggerFactory.getLogger(SenderFunctions.class);

    @Bean
    public Consumer<Notification> sendEmail() {
        return notification -> {
            logger.info("User-{}'s, notification-{} -> Sending email notification: {}", notification.userId(), notification.id(), notification);
            delay(2000);
            logger.info("EMAIL SENT! User-{}'s, notification-{}, notification: {}", notification.userId(), notification.id(), notification);
        };
    }

    @Bean
    public Consumer<Notification> sendSms() {
        return notification -> {
            logger.info("User-{}'s, notification-{} -> Sending SMS notification: {}", notification.userId(), notification.id(), notification);
            delay(3000);
            logger.info("SMS SENT! User-{}'s, notification-{}, notification: {}", notification.userId(), notification.id(), notification);
        };
    }

    @Bean
    public Consumer<Notification> sendPush() {
        return notification -> {
            logger.info("User-{}'s, notification-{} -> Sending push notification: {}", notification.userId(), notification.id(), notification);
            delay(1000);
            logger.info("PUSH SENT! User-{}'s, notification-{}, notification: {}", notification.userId(), notification.id(), notification);
        };
    }

    private static void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
