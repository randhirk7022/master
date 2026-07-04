package com.schoolapp.notice.dto;

import com.schoolapp.notice.entity.NoticeStatus;
import com.schoolapp.notice.entity.NoticeTargetType;
import java.time.LocalDateTime;
import java.util.List;

public class NoticeResponses {

    public static class NoticeResponse {
        private Long id;
        private String title;
        private String content;
        private NoticeTargetType targetType;
        private String targetRole;
        private Long classId;
        private Long sectionId;
        private Long publishedByUserId;
        private LocalDateTime publishedAt;
        private NoticeStatus status;
        private List<NoticeRecipientResponse> recipients;

        public NoticeResponse(Long id, String title, String content, NoticeTargetType targetType,
                              String targetRole, Long classId, Long sectionId, Long publishedByUserId,
                              LocalDateTime publishedAt, NoticeStatus status,
                              List<NoticeRecipientResponse> recipients) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.targetType = targetType;
            this.targetRole = targetRole;
            this.classId = classId;
            this.sectionId = sectionId;
            this.publishedByUserId = publishedByUserId;
            this.publishedAt = publishedAt;
            this.status = status;
            this.recipients = recipients;
        }

        public Long getId() { return id; }
        public String getTitle() { return title; }
        public String getContent() { return content; }
        public NoticeTargetType getTargetType() { return targetType; }
        public String getTargetRole() { return targetRole; }
        public Long getClassId() { return classId; }
        public Long getSectionId() { return sectionId; }
        public Long getPublishedByUserId() { return publishedByUserId; }
        public LocalDateTime getPublishedAt() { return publishedAt; }
        public NoticeStatus getStatus() { return status; }
        public List<NoticeRecipientResponse> getRecipients() { return recipients; }
    }

    public static class NoticeRecipientResponse {
        private Long id;
        private Long userId;
        private boolean readStatus;
        private LocalDateTime readAt;

        public NoticeRecipientResponse(Long id, Long userId, boolean readStatus, LocalDateTime readAt) {
            this.id = id;
            this.userId = userId;
            this.readStatus = readStatus;
            this.readAt = readAt;
        }

        public Long getId() { return id; }
        public Long getUserId() { return userId; }
        public boolean isReadStatus() { return readStatus; }
        public LocalDateTime getReadAt() { return readAt; }
    }
}