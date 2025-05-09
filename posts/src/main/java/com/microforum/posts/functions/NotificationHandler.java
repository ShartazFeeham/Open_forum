package com.microforum.posts.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class NotificationHandler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationHandler.class);

    @Bean
    public Consumer<Notification> handleDeliveredNotification() {
        return (notification) -> {
            notification.notificationTypes().forEach(type -> {
                logger.info("User-{}'s, notification-{} -> Notification type: {}", notification.userId(), notification.id(), type);
            });
        };
    }

}
