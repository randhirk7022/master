package com.schoolapp.notice.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.notice.dto.NoticeRequests.*;
import com.schoolapp.notice.dto.NoticeResponses.*;
import com.schoolapp.notice.entity.NoticeStatus;
import com.schoolapp.notice.service.NoticeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
public class NoticeController {

    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<NoticeResponse> createNotice(@Valid @RequestBody CreateNoticeRequest request) {
        return ApiResponse.success("Notice created successfully", noticeService.createNotice(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<List<NoticeResponse>> getNotices(
            @RequestParam(required = false) NoticeStatus status,
            @RequestParam(required = false) Long userId
    ) {
        return ApiResponse.success("Notices fetched successfully", noticeService.getNotices(status, userId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<NoticeResponse> getNoticeById(@PathVariable Long id) {
        return ApiResponse.success("Notice fetched successfully", noticeService.getNoticeById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<NoticeResponse> updateNotice(
            @PathVariable Long id,
            @RequestBody UpdateNoticeRequest request
    ) {
        return ApiResponse.success("Notice updated successfully", noticeService.updateNotice(id, request));
    }

    @PostMapping("/{id}/publish")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<NoticeResponse> publishNotice(@PathVariable Long id) {
        return ApiResponse.success("Notice published successfully", noticeService.publishNotice(id));
    }

    @PostMapping("/{id}/archive")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<NoticeResponse> archiveNotice(@PathVariable Long id) {
        return ApiResponse.success("Notice archived successfully", noticeService.archiveNotice(id));
    }

    @PostMapping("/{id}/read")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<NoticeRecipientResponse> markAsRead(
            @PathVariable Long id,
            @RequestParam Long userId
    ) {
        return ApiResponse.success("Notice marked as read", noticeService.markAsRead(id, userId));
    }
}