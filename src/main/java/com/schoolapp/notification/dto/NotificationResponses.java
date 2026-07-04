package com.schoolapp.notification.dto;

import com.schoolapp.notification.entity.NotificationChannel;
import com.schoolapp.notification.entity.NotificationStatus;
import java.util.List;

public class NotificationResponses {

    public static class NotificationRequestResponse {
        private Long id;
        private Long templateId;
        private NotificationChannel channel;
        private String triggeredByModule;
        private NotificationStatus status;
        private List<NotificationRecipientResponse> recipients;

        public NotificationRequestResponse(Long id, Long templateId, NotificationChannel channel,
                                           String triggeredByModule, NotificationStatus status,
                                           List<NotificationRecipientResponse> recipients) {
            this.id = id;
            this.templateId = templateId;
            this.channel = channel;
            this.triggeredByModule = triggeredByModule;
            this.status = status;
            this.recipients = recipients;
        }

        public Long getId() { return id; }
        public Long getTemplateId() { return templateId; }
        public NotificationChannel getChannel() { return channel; }
        public String getTriggeredByModule() { return triggeredByModule; }
        public NotificationStatus getStatus() { return status; }
        public List<NotificationRecipientResponse> getRecipients() { return recipients; }
    }

    public static class NotificationRecipientResponse {
        private Long id;
        private Long recipientUserId;
        private String phoneNumber;
        private String whatsappNumber;
        private NotificationStatus status;

        public NotificationRecipientResponse(Long id, Long recipientUserId, String phoneNumber,
                                             String whatsappNumber, NotificationStatus status) {
            this.id = id;
            this.recipientUserId = recipientUserId;
            this.phoneNumber = phoneNumber;
            this.whatsappNumber = whatsappNumber;
            this.status = status;
        }

        public Long getId() { return id; }
        public Long getRecipientUserId() { return recipientUserId; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getWhatsappNumber() { return whatsappNumber; }
        public NotificationStatus getStatus() { return status; }
    }
}