package com.schoolapp.recording.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.recording.dto.RecordingRequests.*;
import com.schoolapp.recording.dto.RecordingResponses.*;
import com.schoolapp.recording.service.RecordedClassService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recorded-classes")
public class RecordedClassController {

    private final RecordedClassService recordedClassService;

    public RecordedClassController(RecordedClassService recordedClassService) {
        this.recordedClassService = recordedClassService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<RecordedClassResponse> createRecordedClass(
            @Valid @RequestBody CreateRecordedClassRequest request
    ) {
        return ApiResponse.success("Recorded class created successfully",
                recordedClassService.createRecordedClass(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<List<RecordedClassResponse>> getRecordedClasses(
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long sectionId,
            @RequestParam(required = false) Long teacherId
    ) {
        return ApiResponse.success("Recorded classes fetched successfully",
                recordedClassService.getRecordedClasses(classId, sectionId, teacherId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<RecordedClassResponse> getRecordedClassById(@PathVariable Long id) {
        return ApiResponse.success("Recorded class fetched successfully",
                recordedClassService.getRecordedClassById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<RecordedClassResponse> updateRecordedClass(
            @PathVariable Long id,
            @RequestBody UpdateRecordedClassRequest request
    ) {
        return ApiResponse.success("Recorded class updated successfully",
                recordedClassService.updateRecordedClass(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<RecordedClassResponse> deleteRecordedClass(@PathVariable Long id) {
        return ApiResponse.success("Recorded class deleted successfully",
                recordedClassService.deleteRecordedClass(id));
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<List<RecordedClassResponse>> getStudentRecordedClasses(
            @PathVariable Long studentId,
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long sectionId
    ) {
        return ApiResponse.success("Student recorded classes fetched successfully",
                recordedClassService.getStudentRecordedClasses(studentId, classId, sectionId));
    }

    @GetMapping("/{id}/playback-url")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT')")
    public ApiResponse<PlaybackUrlResponse> getPlaybackUrl(
            @PathVariable Long id,
            @RequestParam Long studentId,
            HttpServletRequest request
    ) {
        String ipAddress = request.getRemoteAddr();
        String deviceInfo = request.getHeader("User-Agent");

        return ApiResponse.success("Playback URL generated successfully",
                recordedClassService.getPlaybackUrl(id, studentId, ipAddress, deviceInfo));
    }

    @PostMapping("/{id}/progress")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT')")
    public ApiResponse<StudentRecordingProgressResponse> updateProgress(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProgressRequest request
    ) {
        return ApiResponse.success("Recording progress updated successfully",
                recordedClassService.updateProgress(id, request));
    }

    @GetMapping("/student/{studentId}/progress")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<List<StudentRecordingProgressResponse>> getStudentProgress(
            @PathVariable Long studentId
    ) {
        return ApiResponse.success("Student recording progress fetched successfully",
                recordedClassService.getStudentProgress(studentId));
    }
}