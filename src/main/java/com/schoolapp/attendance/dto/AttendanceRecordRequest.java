package com.schoolapp.attendance.dto;

import com.schoolapp.attendance.entity.AttendanceStatus;
import jakarta.validation.constraints.NotNull;

public class AttendanceRecordRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private AttendanceStatus status;

    private String remarks;

    public Long getStudentId() {
        return studentId;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public String getRemarks() {
        return remarks;
    }
}