package com.microforum.posts.functions;

import java.time.LocalDateTime;
import java.util.List;

public record Notification(Long id, LocalDateTime originateAt, LocalDateTime expireAt, boolean seen,
                           String title, String message, String url, String prefix, String suffix,
                           Long userId, String email, String phoneNo,
                           List<NotificationType> notificationTypes, String source) {
}
