package com.schoolapp.audit.service;

import com.schoolapp.audit.dto.AuditRequests.*;
import com.schoolapp.audit.dto.AuditResponses.*;
import com.schoolapp.audit.entity.AuditAction;
import com.schoolapp.audit.entity.AuditLog;
import com.schoolapp.audit.repository.AuditLogRepository;
import com.schoolapp.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public AuditLogResponse createAuditLog(CreateAuditLogRequest request) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId(request.getUserId());
        auditLog.setUsername(request.getUsername());
        auditLog.setAction(request.getAction());
        auditLog.setModuleName(request.getModuleName());
        auditLog.setEntityName(request.getEntityName());
        auditLog.setEntityId(request.getEntityId());
        auditLog.setDescription(request.getDescription());
        auditLog.setIpAddress(request.getIpAddress());
        auditLog.setUserAgent(request.getUserAgent());

        return toResponse(auditLogRepository.save(auditLog));
    }

    public AuditLogResponse log(AuditAction action,
                                String moduleName,
                                String entityName,
                                Long entityId,
                                String description,
                                Long userId,
                                String username,
                                String ipAddress,
                                String userAgent) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(action);
        auditLog.setModuleName(moduleName);
        auditLog.setEntityName(entityName);
        auditLog.setEntityId(entityId);
        auditLog.setDescription(description);
        auditLog.setUserId(userId);
        auditLog.setUsername(username);
        auditLog.setIpAddress(ipAddress);
        auditLog.setUserAgent(userAgent);

        return toResponse(auditLogRepository.save(auditLog));
    }

    public List<AuditLogResponse> getAuditLogs(Long userId,
                                               String username,
                                               String moduleName,
                                               AuditAction action,
                                               LocalDateTime startDate,
                                               LocalDateTime endDate) {
        if (userId != null) {
            return auditLogRepository.findByUserId(userId).stream().map(this::toResponse).toList();
        }

        if (username != null) {
            return auditLogRepository.findByUsername(username).stream().map(this::toResponse).toList();
        }

        if (moduleName != null) {
            return auditLogRepository.findByModuleName(moduleName).stream().map(this::toResponse).toList();
        }

        if (action != null) {
            return auditLogRepository.findByAction(action).stream().map(this::toResponse).toList();
        }

        if (startDate != null && endDate != null) {
            return auditLogRepository.findByCreatedAtBetween(startDate, endDate)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        return auditLogRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AuditLogResponse getAuditLogById(Long id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Audit log not found with id: " + id));

        return toResponse(auditLog);
    }

    private AuditLogResponse toResponse(AuditLog auditLog) {
        return new AuditLogResponse(
                auditLog.getId(),
                auditLog.getUserId(),
                auditLog.getUsername(),
                auditLog.getAction(),
                auditLog.getModuleName(),
                auditLog.getEntityName(),
                auditLog.getEntityId(),
                auditLog.getDescription(),
                auditLog.getIpAddress(),
                auditLog.getUserAgent(),
                auditLog.getCreatedAt()
        );
    }
}