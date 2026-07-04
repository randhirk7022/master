package com.schoolapp.notification.provider;

import com.schoolapp.notification.entity.NotificationChannel;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationProvider implements NotificationProvider {

    @Override
    public NotificationSendResponse send(NotificationSendRequest request) {
        return new NotificationSendResponse(
                "sms_msg_" + System.currentTimeMillis(),
                "{\"provider\":\"SMS\",\"status\":\"sent\"}",
                true
        );
    }

    @Override
    public boolean verifyWebhook(String payload, String signature) {
        return true;
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }

    @Override
    public String getProviderName() {
        return "SMS_PROVIDER";
    }
}