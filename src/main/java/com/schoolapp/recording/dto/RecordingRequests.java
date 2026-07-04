package com.schoolapp.recording.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class RecordingRequests {

    public static class CreateRecordedClassRequest {
        @NotBlank
        private String title;
        private String description;
        private Long liveClassId;
        private Long academicYearId;
        @NotNull
        private Long classId;
        @NotNull
        private Long sectionId;
        @NotNull
        private Long subjectId;
        @NotNull
        private Long teacherId;
        private String storageProvider = "LOCAL";
        @NotBlank
        private String recordingUrl;
        private String storageKey;
        private String thumbnailUrl;
        private Long durationInMinutes;
        private Long fileSize;
        private LocalDateTime availableFrom;
        private LocalDateTime availableUntil;

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public Long getLiveClassId() { return liveClassId; }
        public Long getAcademicYearId() { return academicYearId; }
        public Long getClassId() { return classId; }
        public Long getSectionId() { return sectionId; }
        public Long getSubjectId() { return subjectId; }
        public Long getTeacherId() { return teacherId; }
        public String getStorageProvider() { return storageProvider; }
        public String getRecordingUrl() { return recordingUrl; }
        public String getStorageKey() { return storageKey; }
        public String getThumbnailUrl() { return thumbnailUrl; }
        public Long getDurationInMinutes() { return durationInMinutes; }
        public Long getFileSize() { return fileSize; }
        public LocalDateTime getAvailableFrom() { return availableFrom; }
        public LocalDateTime getAvailableUntil() { return availableUntil; }
    }

    public static class UpdateRecordedClassRequest {
        private String title;
        private String description;
        private String thumbnailUrl;
        private LocalDateTime availableFrom;
        private LocalDateTime availableUntil;

        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getThumbnailUrl() { return thumbnailUrl; }
        public LocalDateTime getAvailableFrom() { return availableFrom; }
        public LocalDateTime getAvailableUntil() { return availableUntil; }
    }

    public static class UpdateProgressRequest {
        @NotNull
        private Long studentId;
        @NotNull
        private Long watchedDurationInMinutes;
        private boolean completed;

        public Long getStudentId() { return studentId; }
        public Long getWatchedDurationInMinutes() { return watchedDurationInMinutes; }
        public boolean isCompleted() { return completed; }
    }
}