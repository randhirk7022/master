package com.schoolapp.audit.dto;

import com.schoolapp.audit.entity.AuditAction;
import java.time.LocalDateTime;

public class AuditResponses {

    public static class AuditLogResponse {
        private Long id;
        private Long userId;
        private String username;
        private AuditAction action;
        private String moduleName;
        private String entityName;
        private Long entityId;
        private String description;
        private String ipAddress;
        private String userAgent;
        private LocalDateTime createdAt;

        public AuditLogResponse(Long id, Long userId, String username, AuditAction action,
                                String moduleName, String entityName, Long entityId,
                                String description, String ipAddress, String userAgent,
                                LocalDateTime createdAt) {
            this.id = id;
            this.userId = userId;
            this.username = username;
            this.action = action;
            this.moduleName = moduleName;
            this.entityName = entityName;
            this.entityId = entityId;
            this.description = description;
            this.ipAddress = ipAddress;
            this.userAgent = userAgent;
            this.createdAt = createdAt;
        }

        public Long getId() { return id; }
        public Long getUserId() { return userId; }
        public String getUsername() { return username; }
        public AuditAction getAction() { return action; }
        public String getModuleName() { return moduleName; }
        public String getEntityName() { return entityName; }
        public Long getEntityId() { return entityId; }
        public String getDescription() { return description; }
        public String getIpAddress() { return ipAddress; }
        public String getUserAgent() { return userAgent; }
        public LocalDateTime getCreatedAt() { return createdAt; }
    }
}