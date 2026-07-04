package com.schoolapp.notification.provider;

import com.schoolapp.notification.entity.NotificationChannel;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class NotificationProviderFactory {

    private final List<NotificationProvider> providers;

    public NotificationProviderFactory(List<NotificationProvider> providers) {
        this.providers = providers;
    }

    public NotificationProvider getProvider(NotificationChannel channel) {
        return providers.stream()
                .filter(provider -> provider.getChannel() == channel)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unsupported notification channel: " + channel));
    }
}