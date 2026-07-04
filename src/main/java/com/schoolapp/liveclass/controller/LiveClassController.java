package com.schoolapp.liveclass.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.liveclass.dto.LiveClassRequests.*;
import com.schoolapp.liveclass.dto.LiveClassResponses.*;
import com.schoolapp.liveclass.service.LiveClassService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/live-classes")
public class LiveClassController {

    private final LiveClassService liveClassService;

    public LiveClassController(LiveClassService liveClassService) {
        this.liveClassService = liveClassService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<LiveClassResponse> createLiveClass(@Valid @RequestBody CreateLiveClassRequest request) {
        return ApiResponse.success("Live class created successfully", liveClassService.createLiveClass(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<List<LiveClassResponse>> getLiveClasses(
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long teacherId
    ) {
        return ApiResponse.success("Live classes fetched successfully",
                liveClassService.getLiveClasses(classId, sectionId, teacherId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<LiveClassResponse> getLiveClassById(@PathVariable Long id) {
        return ApiResponse.success("Live class fetched successfully", liveClassService.getLiveClassById(id));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<List<LiveClassResponse>> getStudentLiveClasses(@PathVariable Long studentId) {
        return ApiResponse.success("Student live classes fetched successfully",
                liveClassService.getStudentLiveClasses(studentId));
    }

    @PostMapping("/{id}/start")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<LiveClassResponse> startLiveClass(@PathVariable Long id) {
        return ApiResponse.success("Live class started successfully", liveClassService.startLiveClass(id));
    }

    @PostMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<LiveClassResponse> completeLiveClass(@PathVariable Long id) {
        return ApiResponse.success("Live class completed successfully", liveClassService.completeLiveClass(id));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<LiveClassResponse> cancelLiveClass(@PathVariable Long id) {
        return ApiResponse.success("Live class cancelled successfully", liveClassService.cancelLiveClass(id));
    }

    @PostMapping("/{id}/reschedule")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<LiveClassResponse> rescheduleLiveClass(
            @PathVariable Long id,
            @Valid @RequestBody RescheduleLiveClassRequest request
    ) {
        return ApiResponse.success("Live class rescheduled successfully",
                liveClassService.rescheduleLiveClass(id, request));
    }

    @PostMapping("/{id}/join")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT')")
    public ApiResponse<JoinLiveClassResponse> joinLiveClass(
            @PathVariable Long id,
            @Valid @RequestBody JoinLiveClassRequest request
    ) {
        return ApiResponse.success("Live class join details fetched successfully",
                liveClassService.joinLiveClass(id, request));
    }

    @PostMapping("/{id}/leave")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT')")
    public ApiResponse<ParticipantResponse> leaveLiveClass(
            @PathVariable Long id,
            @Valid @RequestBody LeaveLiveClassRequest request
    ) {
        return ApiResponse.success("Live class left successfully",
                liveClassService.leaveLiveClass(id, request));
    }

    @PostMapping("/webhooks/{provider}")
    public ApiResponse<String> handleWebhook(
            @PathVariable String provider,
            @Valid @RequestBody LiveClassWebhookRequest request
    ) {
        return ApiResponse.success("Live class webhook processed",
                liveClassService.handleWebhook(provider, request));
    }
}