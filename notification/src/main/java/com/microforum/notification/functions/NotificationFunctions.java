package com.microforum.notification.functions;

import com.microforum.notification.models.dto.NotificationCreateDTO;
import com.microforum.notification.models.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class NotificationFunctions {

    private static final Logger logger = LoggerFactory.getLogger(NotificationFunctions.class);
    private final AnalyzerFunctions analyzerFunctions;
    private final SenderFunctions senderFunctions;
    private static final Random random = new Random();
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    public NotificationFunctions(AnalyzerFunctions analyzerFunctions, SenderFunctions senderFunctions) {
        this.analyzerFunctions = analyzerFunctions;
        this.senderFunctions = senderFunctions;
    }

    @Bean
    public Function<NotificationCreateDTO, Notification> generateNotification() {
        return (notDto) -> {
            Notification notification = new Notification(random.nextLong(), LocalDateTime.now(),
                    LocalDateTime.now().plusSeconds(notDto.durationInSeconds() == null ? 1_000_000_000 : notDto.durationInSeconds()),
                    false, notDto.title(), notDto.message(), notDto.url(), notDto.prefix(), notDto.suffix(),
                    notDto.userId(), notDto.email(), notDto.phoneNo(), notDto.notificationTypes(), notDto.source());

            logger.info("Created notification: {}", notification);
            return notification;
        };
    }

    @Bean
    public Consumer<Notification> sendNotification() {
        return (notification) -> {
            notification.notificationTypes().forEach(type -> {
                boolean allowed = analyzerFunctions.isSendAllowed().apply(notification, type);
                if (allowed) {
                    Future<?> futureTask = executor.submit(() -> {
                        switch (type) {
                            case EMAIL -> senderFunctions.sendEmail().accept(notification);
                            case SMS -> senderFunctions.sendSms().accept(notification);
                            case PUSH -> senderFunctions.sendPush().accept(notification);
                        }
                    });
                }
            });
        };
    }

}
