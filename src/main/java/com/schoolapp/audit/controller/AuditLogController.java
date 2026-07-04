package com.schoolapp.audit.controller;

import com.schoolapp.audit.dto.AuditRequests.*;
import com.schoolapp.audit.dto.AuditResponses.*;
import com.schoolapp.audit.entity.AuditAction;
import com.schoolapp.audit.service.AuditLogService;
import com.schoolapp.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<AuditLogResponse> createAuditLog(
            @Valid @RequestBody CreateAuditLogRequest request
    ) {
        return ApiResponse.success("Audit log created successfully", auditLogService.createAuditLog(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<List<AuditLogResponse>> getAuditLogs(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String moduleName,
            @RequestParam(required = false) AuditAction action,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate
    ) {
        return ApiResponse.success(
                "Audit logs fetched successfully",
                auditLogService.getAuditLogs(userId, username, moduleName, action, startDate, endDate)
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<AuditLogResponse> getAuditLogById(@PathVariable Long id) {
        return ApiResponse.success("Audit log fetched successfully", auditLogService.getAuditLogById(id));
    }
}