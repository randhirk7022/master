package com.schoolapp.notice.dto;

import com.schoolapp.notice.entity.NoticeTargetType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class NoticeRequests {

    public static class CreateNoticeRequest {
        @NotBlank
        private String title;

        @NotBlank
        private String content;

        @NotNull
        private NoticeTargetType targetType;

        private String targetRole;
        private Long classId;
        private Long sectionId;
        private Long publishedByUserId;
        private List<Long> recipientUserIds;

        public String getTitle() { return title; }
        public String getContent() { return content; }
        public NoticeTargetType getTargetType() { return targetType; }
        public String getTargetRole() { return targetRole; }
        public Long getClassId() { return classId; }
        public Long getSectionId() { return sectionId; }
        public Long getPublishedByUserId() { return publishedByUserId; }
        public List<Long> getRecipientUserIds() { return recipientUserIds; }
    }

    public static class UpdateNoticeRequest {
        private String title;
        private String content;
        private NoticeTargetType targetType;
        private String targetRole;
        private Long classId;
        private Long sectionId;
        private List<Long> recipientUserIds;

        public String getTitle() { return title; }
        public String getContent() { return content; }
        public NoticeTargetType getTargetType() { return targetType; }
        public String getTargetRole() { return targetRole; }
        public Long getClassId() { return classId; }
        public Long getSectionId() { return sectionId; }
        public List<Long> getRecipientUserIds() { return recipientUserIds; }
    }
}