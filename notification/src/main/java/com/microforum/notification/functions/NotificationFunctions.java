package com.microforum.notification.functions;

import com.microforum.notification.models.dto.NotificationCreateDTO;
import com.microforum.notification.models.entity.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class NotificationFunctions {

    private static final Logger logger = LoggerFactory.getLogger(NotificationFunctions.class);
    public static final String NOTIFICATION_DELIVERY_EXCHANGE = "notificationDelivered-out-0";
    private final AnalyzerFunctions analyzerFunctions;
    private final SenderFunctions senderFunctions;
    private static final Random random = new Random();
    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final StreamBridge streamBridge;

    public NotificationFunctions(AnalyzerFunctions analyzerFunctions,
                                 SenderFunctions senderFunctions,
                                 StreamBridge streamBridge) {
        this.analyzerFunctions = analyzerFunctions;
        this.senderFunctions = senderFunctions;
        this.streamBridge = streamBridge;
    }

    @Bean
    public Function<NotificationCreateDTO, Notification> generateNotification() {
        return (notDto) -> {
            Notification notification = new Notification(random.nextLong(1_000_000_000_000_000L), LocalDateTime.now(),
                    LocalDateTime.now().plusSeconds(notDto.durationInSeconds() == null ? 1_000_000_000 : notDto.durationInSeconds()),
                    false, notDto.title(), notDto.message(), notDto.url(), notDto.prefix(), notDto.suffix(),
                    notDto.userId(), notDto.email(), notDto.phoneNo(), notDto.notificationTypes(), notDto.source());

            logger.info("User-{}'s, notification created: {}", notDto.userId(), notification);
            return notification;
        };
    }

    @Bean
    public Consumer<Notification> sendNotification() {
        final List<Future<?>> futures = new ArrayList<>();

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
                    futures.add(futureTask);
                }
            });

            waitForAllNotificationsToBeSent(futures, notification);
            publishNotificationsDeliveredEvent(notification);
        };
    }

    private static void waitForAllNotificationsToBeSent(List<Future<?>> futures, Notification notification) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("All notifications were sent for userId: {}, and notificationId: {}", notification.userId(), notification.id());
    }

    private void publishNotificationsDeliveredEvent(Notification notification) {
        streamBridge.send(NOTIFICATION_DELIVERY_EXCHANGE, notification);

        logger.info("Published notification delivery event for userId: {}, and notificationId: {}",
                notification.userId(), notification.id());
    }
}
