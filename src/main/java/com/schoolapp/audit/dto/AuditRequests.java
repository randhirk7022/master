package com.schoolapp.audit.dto;

import com.schoolapp.audit.entity.AuditAction;
import jakarta.validation.constraints.NotNull;

public class AuditRequests {

    public static class CreateAuditLogRequest {
        private Long userId;
        private String username;

        @NotNull
        private AuditAction action;

        private String moduleName;
        private String entityName;
        private Long entityId;
        private String description;
        private String ipAddress;
        private String userAgent;

        public Long getUserId() { return userId; }
        public String getUsername() { return username; }
        public AuditAction getAction() { return action; }
        public String getModuleName() { return moduleName; }
        public String getEntityName() { return entityName; }
        public Long getEntityId() { return entityId; }
        public String getDescription() { return description; }
        public String getIpAddress() { return ipAddress; }
        public String getUserAgent() { return userAgent; }
    }
}