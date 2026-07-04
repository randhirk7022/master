package com.schoolapp.notification.repository;

import com.schoolapp.notification.entity.NotificationWebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationWebhookEventRepository extends JpaRepository<NotificationWebhookEvent, Long> {
    boolean existsByEventId(String eventId);
}