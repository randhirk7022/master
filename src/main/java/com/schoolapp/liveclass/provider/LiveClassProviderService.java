package com.schoolapp.liveclass.provider;

import java.time.LocalDateTime;

public interface LiveClassProviderService {

    ProviderMeetingResponse createMeeting(ProviderMeetingRequest request);

    ProviderMeetingResponse updateMeeting(String providerMeetingId, ProviderMeetingRequest request);

    void cancelMeeting(String providerMeetingId);

    boolean verifyWebhook(String payload, String signature);

    String getProviderName();

    class ProviderMeetingRequest {
        private String title;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private boolean recordingEnabled;

        public ProviderMeetingRequest(String title, LocalDateTime startTime,
                                      LocalDateTime endTime, boolean recordingEnabled) {
            this.title = title;
            this.startTime = startTime;
            this.endTime = endTime;
            this.recordingEnabled = recordingEnabled;
        }

        public String getTitle() { return title; }
        public LocalDateTime getStartTime() { return startTime; }
        public LocalDateTime getEndTime() { return endTime; }
        public boolean isRecordingEnabled() { return recordingEnabled; }
    }

    class ProviderMeetingResponse {
        private String providerMeetingId;
        private String joinUrl;
        private String hostUrl;
        private String password;

        public ProviderMeetingResponse(String providerMeetingId, String joinUrl, String hostUrl, String password) {
            this.providerMeetingId = providerMeetingId;
            this.joinUrl = joinUrl;
            this.hostUrl = hostUrl;
            this.password = password;
        }

        public String getProviderMeetingId() { return providerMeetingId; }
        public String getJoinUrl() { return joinUrl; }
        public String getHostUrl() { return hostUrl; }
        public String getPassword() { return password; }
    }
}