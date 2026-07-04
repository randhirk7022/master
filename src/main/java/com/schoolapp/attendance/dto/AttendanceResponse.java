package com.schoolapp.attendance.dto;

import com.schoolapp.attendance.entity.AttendanceStatus;

import java.time.LocalDate;

public class AttendanceResponse {

    private Long id;
    private Long studentId;
    private Long classId;
    private Long sectionId;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    private Long markedByTeacherId;
    private String remarks;

    public AttendanceResponse(
            Long id,
            Long studentId,
            Long classId,
            Long sectionId,
            LocalDate attendanceDate,
            AttendanceStatus status,
            Long markedByTeacherId,
            String remarks
    ) {
        this.id = id;
        this.studentId = studentId;
        this.classId = classId;
        this.sectionId = sectionId;
        this.attendanceDate = attendanceDate;
        this.status = status;
        this.markedByTeacherId = markedByTeacherId;
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getClassId() {
        return classId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    public Long getMarkedByTeacherId() {
        return markedByTeacherId;
    }

    public String getRemarks() {
        return remarks;
    }
}