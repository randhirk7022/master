package com.schoolapp.audit.repository;

import com.schoolapp.audit.entity.AuditAction;
import com.schoolapp.audit.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByUserId(Long userId);

    List<AuditLog> findByUsername(String username);

    List<AuditLog> findByModuleName(String moduleName);

    List<AuditLog> findByAction(AuditAction action);

    List<AuditLog> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
}