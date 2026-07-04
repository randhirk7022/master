package com.schoolapp.notification.repository;

import com.schoolapp.notification.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
    boolean existsByTemplateName(String templateName);
    Optional<NotificationTemplate> findByTemplateName(String templateName);
}