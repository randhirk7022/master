package com.schoolapp.notification.repository;

import com.schoolapp.notification.entity.NotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Long> {
    List<NotificationRecipient> findByNotificationRequestId(Long notificationRequestId);
}