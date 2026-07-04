package com.schoolapp.liveclass.provider;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LiveClassProviderFactory {

    private final List<LiveClassProviderService> providers;

    public LiveClassProviderFactory(List<LiveClassProviderService> providers) {
        this.providers = providers;
    }

    public LiveClassProviderService getProvider(String providerName) {
        return providers.stream()
                .filter(provider -> provider.getProviderName().equalsIgnoreCase(providerName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unsupported live class provider: " + providerName));
    }
}