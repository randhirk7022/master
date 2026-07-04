package com.schoolapp.notification.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.notification.dto.NotificationRequests.*;
import com.schoolapp.notification.dto.NotificationResponses.*;
import com.schoolapp.notification.entity.NotificationTemplate;
import com.schoolapp.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/templates")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<NotificationTemplate> createTemplate(
            @Valid @RequestBody CreateTemplateRequest request
    ) {
        return ApiResponse.success("Notification template created successfully", notificationService.createTemplate(request));
    }

    @GetMapping("/templates")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<List<NotificationTemplate>> getTemplates() {
        return ApiResponse.success("Notification templates fetched successfully", notificationService.getTemplates());
    }

    @PutMapping("/templates/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<NotificationTemplate> updateTemplate(
            @PathVariable Long id,
            @Valid @RequestBody CreateTemplateRequest request
    ) {
        return ApiResponse.success("Notification template updated successfully", notificationService.updateTemplate(id, request));
    }

    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'ACCOUNTANT')")
    public ApiResponse<NotificationRequestResponse> sendNotification(
            @Valid @RequestBody SendNotificationRequest request
    ) {
        return ApiResponse.success("Notification sent successfully", notificationService.sendNotification(request));
    }

    @GetMapping("/requests")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<List<NotificationRequestResponse>> getRequests() {
        return ApiResponse.success("Notification requests fetched successfully", notificationService.getRequests());
    }

    @GetMapping("/requests/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<NotificationRequestResponse> getRequestById(@PathVariable Long id) {
        return ApiResponse.success("Notification request fetched successfully", notificationService.getRequestById(id));
    }

    @PostMapping("/webhooks/{provider}")
    public ApiResponse<String> handleWebhook(
            @PathVariable String provider,
            @Valid @RequestBody NotificationWebhookRequest request
    ) {
        return ApiResponse.success("Notification webhook processed", notificationService.handleWebhook(provider, request));
    }
}