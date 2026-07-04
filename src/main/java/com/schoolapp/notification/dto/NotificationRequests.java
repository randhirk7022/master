package com.schoolapp.notification.dto;

import com.schoolapp.notification.entity.NotificationChannel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class NotificationRequests {

    public static class CreateTemplateRequest {
        @NotBlank
        private String templateName;
        @NotNull
        private NotificationChannel channel;
        private String subject;
        @NotBlank
        private String body;
        private String providerTemplateId;
        private boolean active = true;

        public String getTemplateName() { return templateName; }
        public NotificationChannel getChannel() { return channel; }
        public String getSubject() { return subject; }
        public String getBody() { return body; }
        public String getProviderTemplateId() { return providerTemplateId; }
        public boolean isActive() { return active; }
    }

    public static class SendNotificationRequest {
        @NotNull
        private Long templateId;
        private String triggeredByModule;

        @Valid
        @NotEmpty
        private List<RecipientRequest> recipients;

        private Map<String, String> variables;
        
        public SendNotificationRequest() {
        	
        }
        
        public SendNotificationRequest(Long templateId, String triggeredByModule,
                List<RecipientRequest> recipients,
                Map<String, String> variables) {
		this.templateId = templateId;
		this.triggeredByModule = triggeredByModule;
		this.recipients = recipients;
		this.variables = variables;
		}

        public Long getTemplateId() { return templateId; }
        public String getTriggeredByModule() { return triggeredByModule; }
        public List<RecipientRequest> getRecipients() { return recipients; }
        public Map<String, String> getVariables() { return variables; }
    }

    public static class RecipientRequest {
        private Long recipientUserId;
        private String phoneNumber;
        private String whatsappNumber;
        
        public RecipientRequest() {
        }
        
        public RecipientRequest(Long recipientUserId, String phoneNumber, String whatsappNumber) {
            this.recipientUserId = recipientUserId;
            this.phoneNumber = phoneNumber;
            this.whatsappNumber = whatsappNumber;
        }

        public Long getRecipientUserId() { return recipientUserId; }
        public String getPhoneNumber() { return phoneNumber; }
        public String getWhatsappNumber() { return whatsappNumber; }
    }

    public static class NotificationWebhookRequest {
        @NotBlank
        private String eventId;
        @NotBlank
        private String eventType;
        @NotBlank
        private String payload;

        public String getEventId() { return eventId; }
        public String getEventType() { return eventType; }
        public String getPayload() { return payload; }
    }
}