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
            logger.info("Sending email notification: {}", notification);
            delay(2000);
            logger.info("Email notification sent: {}", notification);
        };
    }

    @Bean
    public Consumer<Notification> sendSms() {
        return notification -> {
            logger.info("Sending SMS notification: {}", notification);
            delay(3000);
            logger.info("SMS notification sent: {}", notification);
        };
    }

    @Bean
    public Consumer<Notification> sendPush() {
        return notification -> {
            logger.info("Sending push notification: {}", notification);
            delay(1000);
            logger.info("Push notification sent: {}", notification);
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
