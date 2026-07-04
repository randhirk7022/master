package com.schoolapp.recording.dto;

import com.schoolapp.recording.entity.RecordedClassStatus;
import java.time.LocalDateTime;

public class RecordingResponses {

    public static class RecordedClassResponse {
        private Long id;
        private String title;
        private Long liveClassId;
        private Long classId;
        private Long sectionId;
        private Long subjectId;
        private Long teacherId;
        private String recordingUrl;
        private String thumbnailUrl;
        private Long durationInMinutes;
        private RecordedClassStatus status;
        private LocalDateTime availableFrom;
        private LocalDateTime availableUntil;

        public RecordedClassResponse(Long id, String title, Long liveClassId, Long classId,
                                     Long sectionId, Long subjectId, Long teacherId,
                                     String recordingUrl, String thumbnailUrl,
                                     Long durationInMinutes, RecordedClassStatus status,
                                     LocalDateTime availableFrom, LocalDateTime availableUntil) {
            this.id = id;
            this.title = title;
            this.liveClassId = liveClassId;
            this.classId = classId;
            this.sectionId = sectionId;
            this.subjectId = subjectId;
            this.teacherId = teacherId;
            this.recordingUrl = recordingUrl;
            this.thumbnailUrl = thumbnailUrl;
            this.durationInMinutes = durationInMinutes;
            this.status = status;
            this.availableFrom = availableFrom;
            this.availableUntil = availableUntil;
        }

        public Long getId() { return id; }
        public String getTitle() { return title; }
        public Long getLiveClassId() { return liveClassId; }
        public Long getClassId() { return classId; }
        public Long getSectionId() { return sectionId; }
        public Long getSubjectId() { return subjectId; }
        public Long getTeacherId() { return teacherId; }
        public String getRecordingUrl() { return recordingUrl; }
        public String getThumbnailUrl() { return thumbnailUrl; }
        public Long getDurationInMinutes() { return durationInMinutes; }
        public RecordedClassStatus getStatus() { return status; }
        public LocalDateTime getAvailableFrom() { return availableFrom; }
        public LocalDateTime getAvailableUntil() { return availableUntil; }
    }

    public static class PlaybackUrlResponse {
        private Long recordedClassId;
        private Long studentId;
        private String playbackUrl;

        public PlaybackUrlResponse(Long recordedClassId, Long studentId, String playbackUrl) {
            this.recordedClassId = recordedClassId;
            this.studentId = studentId;
            this.playbackUrl = playbackUrl;
        }

        public Long getRecordedClassId() { return recordedClassId; }
        public Long getStudentId() { return studentId; }
        public String getPlaybackUrl() { return playbackUrl; }
    }

    public static class StudentRecordingProgressResponse {
        private Long id;
        private Long recordedClassId;
        private Long studentId;
        private Long watchedDurationInMinutes;
        private boolean completed;
        private LocalDateTime lastWatchedAt;

        public StudentRecordingProgressResponse(Long id, Long recordedClassId, Long studentId,
                                                Long watchedDurationInMinutes, boolean completed,
                                                LocalDateTime lastWatchedAt) {
            this.id = id;
            this.recordedClassId = recordedClassId;
            this.studentId = studentId;
            this.watchedDurationInMinutes = watchedDurationInMinutes;
            this.completed = completed;
            this.lastWatchedAt = lastWatchedAt;
        }

        public Long getId() { return id; }
        public Long getRecordedClassId() { return recordedClassId; }
        public Long getStudentId() { return studentId; }
        public Long getWatchedDurationInMinutes() { return watchedDurationInMinutes; }
        public boolean isCompleted() { return completed; }
        public LocalDateTime getLastWatchedAt() { return lastWatchedAt; }
    }
}