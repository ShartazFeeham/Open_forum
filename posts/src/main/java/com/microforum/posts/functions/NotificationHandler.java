package com.microforum.posts.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class NotificationHandler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationHandler.class);

    @Bean
    public Function<Object, String> handleDeliveredNotification() {
        return message -> {
            logger.info(message.toString());
            return message.toString();
        };
    }

}
