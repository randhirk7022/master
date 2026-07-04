package com.schoolapp.attendance.controller;

import com.schoolapp.attendance.dto.*;
import com.schoolapp.attendance.service.AttendanceService;
import com.schoolapp.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<AttendanceResponse>> markAttendance(
            @Valid @RequestBody MarkAttendanceRequest request
    ) {
        return ApiResponse.success(
                "Attendance marked successfully",
                attendanceService.markAttendance(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<AttendanceResponse>> getAttendance(
            @RequestParam Long classId,
            @RequestParam Long sectionId,
            @RequestParam LocalDate attendanceDate
    ) {
        return ApiResponse.success(
                "Attendance fetched successfully",
                attendanceService.getAttendance(classId, sectionId, attendanceDate)
        );
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<AttendanceSummaryResponse> getStudentSummary(
            @RequestParam Long studentId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ApiResponse.success(
                "Attendance summary fetched successfully",
                attendanceService.getStudentSummary(studentId, startDate, endDate)
        );
    }
}