package com.schoolapp.liveclass.dto;

import com.schoolapp.liveclass.entity.LiveClassStatus;
import com.schoolapp.liveclass.entity.ParticipantStatus;
import java.time.LocalDateTime;
import java.util.List;

public class LiveClassResponses {

    public static class LiveClassResponse {
        private Long id;
        private String title;
        private Long classId;
        private Long sectionId;
        private Long subjectId;
        private Long teacherId;
        private LocalDateTime scheduledStartTime;
        private LocalDateTime scheduledEndTime;
        private String providerName;
        private String providerMeetingId;
        private String joinUrl;
        private String hostUrl;
        private LiveClassStatus status;
        private boolean recordingEnabled;
        private List<ParticipantResponse> participants;

        public LiveClassResponse(Long id, String title, Long classId, Long sectionId, Long subjectId,
                                 Long teacherId, LocalDateTime scheduledStartTime,
                                 LocalDateTime scheduledEndTime, String providerName,
                                 String providerMeetingId, String joinUrl, String hostUrl,
                                 LiveClassStatus status, boolean recordingEnabled,
                                 List<ParticipantResponse> participants) {
            this.id = id;
            this.title = title;
            this.classId = classId;
            this.sectionId = sectionId;
            this.subjectId = subjectId;
            this.teacherId = teacherId;
            this.scheduledStartTime = scheduledStartTime;
            this.scheduledEndTime = scheduledEndTime;
            this.providerName = providerName;
            this.providerMeetingId = providerMeetingId;
            this.joinUrl = joinUrl;
            this.hostUrl = hostUrl;
            this.status = status;
            this.recordingEnabled = recordingEnabled;
            this.participants = participants;
        }

        public Long getId() { return id; }
        public String getTitle() { return title; }
        public Long getClassId() { return classId; }
        public Long getSectionId() { return sectionId; }
        public Long getSubjectId() { return subjectId; }
        public Long getTeacherId() { return teacherId; }
        public LocalDateTime getScheduledStartTime() { return scheduledStartTime; }
        public LocalDateTime getScheduledEndTime() { return scheduledEndTime; }
        public String getProviderName() { return providerName; }
        public String getProviderMeetingId() { return providerMeetingId; }
        public String getJoinUrl() { return joinUrl; }
        public String getHostUrl() { return hostUrl; }
        public LiveClassStatus getStatus() { return status; }
        public boolean isRecordingEnabled() { return recordingEnabled; }
        public List<ParticipantResponse> getParticipants() { return participants; }
    }

    public static class ParticipantResponse {
        private Long id;
        private Long studentId;
        private LocalDateTime joinedAt;
        private LocalDateTime leftAt;
        private Long durationInMinutes;
        private ParticipantStatus participationStatus;

        public ParticipantResponse(Long id, Long studentId, LocalDateTime joinedAt,
                                   LocalDateTime leftAt, Long durationInMinutes,
                                   ParticipantStatus participationStatus) {
            this.id = id;
            this.studentId = studentId;
            this.joinedAt = joinedAt;
            this.leftAt = leftAt;
            this.durationInMinutes = durationInMinutes;
            this.participationStatus = participationStatus;
        }

        public Long getId() { return id; }
        public Long getStudentId() { return studentId; }
        public LocalDateTime getJoinedAt() { return joinedAt; }
        public LocalDateTime getLeftAt() { return leftAt; }
        public Long getDurationInMinutes() { return durationInMinutes; }
        public ParticipantStatus getParticipationStatus() { return participationStatus; }
    }

    public static class JoinLiveClassResponse {
        private Long liveClassId;
        private Long studentId;
        private String joinUrl;
        private String password;

        public JoinLiveClassResponse(Long liveClassId, Long studentId, String joinUrl, String password) {
            this.liveClassId = liveClassId;
            this.studentId = studentId;
            this.joinUrl = joinUrl;
            this.password = password;
        }

        public Long getLiveClassId() { return liveClassId; }
        public Long getStudentId() { return studentId; }
        public String getJoinUrl() { return joinUrl; }
        public String getPassword() { return password; }
    }
}