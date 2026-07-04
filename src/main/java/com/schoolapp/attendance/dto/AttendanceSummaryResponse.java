package com.schoolapp.attendance.dto;

public class AttendanceSummaryResponse {

    private Long studentId;
    private long totalDays;
    private long presentDays;
    private long absentDays;
    private long lateDays;
    private long leaveDays;
    private double attendancePercentage;

    public AttendanceSummaryResponse(
            Long studentId,
            long totalDays,
            long presentDays,
            long absentDays,
            long lateDays,
            long leaveDays
    ) {
        this.studentId = studentId;
        this.totalDays = totalDays;
        this.presentDays = presentDays;
        this.absentDays = absentDays;
        this.lateDays = lateDays;
        this.leaveDays = leaveDays;
        this.attendancePercentage = totalDays == 0
                ? 0
                : ((double) presentDays / totalDays) * 100;
    }

    public Long getStudentId() {
        return studentId;
    }

    public long getTotalDays() {
        return totalDays;
    }

    public long getPresentDays() {
        return presentDays;
    }

    public long getAbsentDays() {
        return absentDays;
    }

    public long getLateDays() {
        return lateDays;
    }

    public long getLeaveDays() {
        return leaveDays;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }
}