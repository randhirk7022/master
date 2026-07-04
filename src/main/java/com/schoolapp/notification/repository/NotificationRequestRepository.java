package com.schoolapp.notification.repository;

import com.schoolapp.notification.entity.NotificationRequest;
import com.schoolapp.notification.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRequestRepository extends JpaRepository<NotificationRequest, Long> {
    List<NotificationRequest> findByStatus(NotificationStatus status);
}