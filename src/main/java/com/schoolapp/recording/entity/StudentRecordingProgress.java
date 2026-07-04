package com.schoolapp.recording.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "student_recording_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"recorded_class_id", "student_id"})
)
public class StudentRecordingProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recorded_class_id", nullable = false)
    private Long recordedClassId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    private Long watchedDurationInMinutes = 0L;
    private boolean completed = false;
    private LocalDateTime lastWatchedAt;

    @PrePersist
    void onCreate() {
        lastWatchedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        lastWatchedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getRecordedClassId() { return recordedClassId; }
    public void setRecordedClassId(Long recordedClassId) { this.recordedClassId = recordedClassId; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Long getWatchedDurationInMinutes() { return watchedDurationInMinutes; }
    public void setWatchedDurationInMinutes(Long watchedDurationInMinutes) { this.watchedDurationInMinutes = watchedDurationInMinutes; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public LocalDateTime getLastWatchedAt() { return lastWatchedAt; }
}