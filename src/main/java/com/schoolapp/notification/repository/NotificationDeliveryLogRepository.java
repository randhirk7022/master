package com.schoolapp.notification.repository;

import com.schoolapp.notification.entity.NotificationDeliveryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NotificationDeliveryLogRepository extends JpaRepository<NotificationDeliveryLog, Long> {
    List<NotificationDeliveryLog> findByRecipientId(Long recipientId);
    Optional<NotificationDeliveryLog> findByProviderMessageId(String providerMessageId);
}