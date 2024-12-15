package com.microforum.notification.functions;

import com.microforum.notification.models.entity.Notification;
import com.microforum.notification.models.entity.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;
import java.util.function.BiFunction;

@Configuration
public class AnalyzerFunctions {

    private static final Logger logger = LoggerFactory.getLogger(AnalyzerFunctions.class);
    private static final Random random = new Random();

    @Bean
    public BiFunction<Notification, NotificationType, Boolean> isSendAllowed() {
        return (notification, type) -> {
            // Check user notification preferences, notification redundancy, notification type, notification source
            // And determine whether to send or not
            boolean allowed = random.nextInt(10) > 3; // Randomly giving 70% chance to send notification
            logger.info("Sending allowed: {}, Notification: {}, Type: {}", allowed, notification, type);
            return allowed;
        };
    }
}
