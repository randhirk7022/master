package com.schoolapp.liveclass.provider;

import org.springframework.stereotype.Service;

@Service
public class ZoomLiveClassProviderService implements LiveClassProviderService {

    @Override
    public ProviderMeetingResponse createMeeting(ProviderMeetingRequest request) {
        String meetingId = "zoom_meeting_" + System.currentTimeMillis();

        return new ProviderMeetingResponse(
                meetingId,
                "https://zoom.example.com/join/" + meetingId,
                "https://zoom.example.com/host/" + meetingId,
                "123456"
        );
    }

    @Override
    public ProviderMeetingResponse updateMeeting(String providerMeetingId, ProviderMeetingRequest request) {
        return new ProviderMeetingResponse(
                providerMeetingId,
                "https://zoom.example.com/join/" + providerMeetingId,
                "https://zoom.example.com/host/" + providerMeetingId,
                "123456"
        );
    }

    @Override
    public void cancelMeeting(String providerMeetingId) {
    }

    @Override
    public boolean verifyWebhook(String payload, String signature) {
        return true;
    }

    @Override
    public String getProviderName() {
        return "ZOOM";
    }
}