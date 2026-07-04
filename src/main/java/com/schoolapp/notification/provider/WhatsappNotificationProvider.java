package com.schoolapp.notification.provider;

import com.schoolapp.notification.entity.NotificationChannel;
import org.springframework.stereotype.Service;

@Service
public class WhatsappNotificationProvider implements NotificationProvider {

    @Override
    public NotificationSendResponse send(NotificationSendRequest request) {
        return new NotificationSendResponse(
                "whatsapp_msg_" + System.currentTimeMillis(),
                "{\"provider\":\"WHATSAPP\",\"status\":\"sent\"}",
                true
        );
    }

    @Override
    public boolean verifyWebhook(String payload, String signature) {
        return true;
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.WHATSAPP;
    }

    @Override
    public String getProviderName() {
        return "WHATSAPP_PROVIDER";
    }
}