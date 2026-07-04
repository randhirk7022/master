package com.schoolapp.liveclass.entity;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "live_class_participants",
        uniqueConstraints = @UniqueConstraint(columnNames = {"live_class_id", "student_id"})
)
public class LiveClassParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "live_class_id", nullable = false)
    private Long liveClassId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;
    private Long durationInMinutes = 0L;

    @Enumerated(EnumType.STRING)
    private ParticipantStatus participationStatus = ParticipantStatus.INVITED;

    public Long getId() { return id; }
    public Long getLiveClassId() { return liveClassId; }
    public void setLiveClassId(Long liveClassId) { this.liveClassId = liveClassId; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public LocalDateTime getJoinedAt() { return joinedAt; }
    public void setJoinedAt(LocalDateTime joinedAt) { this.joinedAt = joinedAt; }
    public LocalDateTime getLeftAt() { return leftAt; }
    public void setLeftAt(LocalDateTime leftAt) { this.leftAt = leftAt; }
    public Long getDurationInMinutes() { return durationInMinutes; }
    public void setDurationInMinutes(Long durationInMinutes) { this.durationInMinutes = durationInMinutes; }
    public ParticipantStatus getParticipationStatus() { return participationStatus; }
    public void setParticipationStatus(ParticipantStatus participationStatus) { this.participationStatus = participationStatus; }

    public void calculateDuration() {
        if (joinedAt != null && leftAt != null) {
            durationInMinutes = Duration.between(joinedAt, leftAt).toMinutes();
        }
    }
}