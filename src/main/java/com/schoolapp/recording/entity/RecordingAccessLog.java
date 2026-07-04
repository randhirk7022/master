package com.schoolapp.recording.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recording_access_logs")
public class RecordingAccessLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recordedClassId;
    private Long studentId;
    private String ipAddress;
    private String deviceInfo;
    private LocalDateTime accessedAt;

    @PrePersist
    void onCreate() {
        accessedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getRecordedClassId() { return recordedClassId; }
    public void setRecordedClassId(Long recordedClassId) { this.recordedClassId = recordedClassId; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getDeviceInfo() { return deviceInfo; }
    public void setDeviceInfo(String deviceInfo) { this.deviceInfo = deviceInfo; }
    public LocalDateTime getAccessedAt() { return accessedAt; }
}