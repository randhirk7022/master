package com.schoolapp.liveclass.entity;

import com.schoolapp.attendance.entity.AttendanceStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "live_class_attendance")
public class LiveClassAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long liveClassId;
    private Long studentId;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    private Long durationInMinutes;
    private LocalDateTime markedAt;

    @PrePersist
    void onCreate() {
        markedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getLiveClassId() { return liveClassId; }
    public void setLiveClassId(Long liveClassId) { this.liveClassId = liveClassId; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public AttendanceStatus getAttendanceStatus() { return attendanceStatus; }
    public void setAttendanceStatus(AttendanceStatus attendanceStatus) { this.attendanceStatus = attendanceStatus; }
    public Long getDurationInMinutes() { return durationInMinutes; }
    public void setDurationInMinutes(Long durationInMinutes) { this.durationInMinutes = durationInMinutes; }
}