package com.schoolapp.attendance.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class MarkAttendanceRequest {

    @NotNull
    private Long classId;

    @NotNull
    private Long sectionId;

    @NotNull
    private LocalDate attendanceDate;

    private Long markedByTeacherId;

    @Valid
    @NotEmpty
    private List<AttendanceRecordRequest> records;

    public Long getClassId() {
        return classId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public Long getMarkedByTeacherId() {
        return markedByTeacherId;
    }

    public List<AttendanceRecordRequest> getRecords() {
        return records;
    }
}