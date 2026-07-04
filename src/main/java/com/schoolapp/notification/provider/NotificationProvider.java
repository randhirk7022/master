package com.schoolapp.notification.provider;

import com.schoolapp.notification.entity.NotificationChannel;

public interface NotificationProvider {

    NotificationSendResponse send(NotificationSendRequest request);

    boolean verifyWebhook(String payload, String signature);

    NotificationChannel getChannel();

    String getProviderName();

    class NotificationSendRequest {
        private String to;
        private String message;

        public NotificationSendRequest(String to, String message) {
            this.to = to;
            this.message = message;
        }

        public String getTo() { return to; }
        public String getMessage() { return message; }
    }

    class NotificationSendResponse {
        private String providerMessageId;
        private String rawResponse;
        private boolean success;

        public NotificationSendResponse(String providerMessageId, String rawResponse, boolean success) {
            this.providerMessageId = providerMessageId;
            this.rawResponse = rawResponse;
            this.success = success;
        }

        public String getProviderMessageId() { return providerMessageId; }
        public String getRawResponse() { return rawResponse; }
        public boolean isSuccess() { return success; }
    }
}