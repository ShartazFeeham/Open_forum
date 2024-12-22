package com.microforum.notification.models.dto;

import com.microforum.notification.models.entity.NotificationType;
import reactor.util.annotation.NonNull;

import java.util.List;

public record NotificationCreateDTO(String title, @NonNull String message, String url,
                                    String prefix, String suffix, Long durationInSeconds,
                                    @NonNull Long userId, String email, String phoneNo,
                                    @NonNull List<NotificationType> notificationTypes, String source) {
}
