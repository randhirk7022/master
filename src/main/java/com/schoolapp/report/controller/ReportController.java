package com.schoolapp.report.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.report.dto.ReportResponses.*;
import com.schoolapp.report.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<StudentReportResponse> getStudentReport() {
        return ApiResponse.success("Student report fetched successfully", reportService.getStudentReport());
    }

    @GetMapping("/attendance")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<AttendanceReportResponse> getAttendanceReport() {
        return ApiResponse.success("Attendance report fetched successfully", reportService.getAttendanceReport());
    }

    @GetMapping("/exams")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<ExamReportResponse> getExamReport() {
        return ApiResponse.success("Exam report fetched successfully", reportService.getExamReport());
    }

    @GetMapping("/fees")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<FeeReportResponse> getFeeReport() {
        return ApiResponse.success("Fee report fetched successfully", reportService.getFeeReport());
    }

    @GetMapping("/payments")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<PaymentReportResponse> getPaymentReport() {
        return ApiResponse.success("Payment report fetched successfully", reportService.getPaymentReport());
    }

    @GetMapping("/live-classes")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<LiveClassReportResponse> getLiveClassReport() {
        return ApiResponse.success("Live class report fetched successfully", reportService.getLiveClassReport());
    }

    @GetMapping("/recorded-classes")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<RecordedClassReportResponse> getRecordedClassReport() {
        return ApiResponse.success("Recorded class report fetched successfully", reportService.getRecordedClassReport());
    }
}