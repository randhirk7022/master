package com.schoolapp.liveclass.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class LiveClassRequests {

    public static class CreateLiveClassRequest {
        @NotBlank
        private String title;
        private String description;
        private Long academicYearId;
        @NotNull
        private Long classId;
        @NotNull
        private Long sectionId;
        @NotNull
        private Long subjectId;
        @NotNull
        private Long teacherId;
        @NotNull
        private LocalDateTime scheduledStartTime;
        @NotNull
        private LocalDateTime scheduledEndTime;
        @NotBlank
        private String providerName;
        private boolean recordingEnabled = true;
        private List<Long> studentIds;

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public Long getAcademicYearId() { return academicYearId; }
        public Long getClassId() { return classId; }
        public Long getSectionId() { return sectionId; }
        public Long getSubjectId() { return subjectId; }
        public Long getTeacherId() { return teacherId; }
        public LocalDateTime getScheduledStartTime() { return scheduledStartTime; }
        public LocalDateTime getScheduledEndTime() { return scheduledEndTime; }
        public String getProviderName() { return providerName; }
        public boolean isRecordingEnabled() { return recordingEnabled; }
        public List<Long> getStudentIds() { return studentIds; }
    }

    public static class RescheduleLiveClassRequest {
        @NotNull
        private LocalDateTime scheduledStartTime;
        @NotNull
        private LocalDateTime scheduledEndTime;

        public LocalDateTime getScheduledStartTime() { return scheduledStartTime; }
        public LocalDateTime getScheduledEndTime() { return scheduledEndTime; }
    }

    public static class JoinLiveClassRequest {
        @NotNull
        private Long studentId;

        public Long getStudentId() { return studentId; }
    }

    public static class LeaveLiveClassRequest {
        @NotNull
        private Long studentId;

        public Long getStudentId() { return studentId; }
    }

    public static class LiveClassWebhookRequest {
        private Long liveClassId;
        @NotBlank
        private String eventType;
        @NotBlank
        private String payload;

        public Long getLiveClassId() { return liveClassId; }
        public String getEventType() { return eventType; }
        public String getPayload() { return payload; }
    }
}