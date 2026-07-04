package com.schoolapp.liveclass.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "live_class_provider_events")
public class LiveClassProviderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long liveClassId;
    private String providerName;
    private String eventType;

    @Column(length = 8000)
    private String payload;

    private boolean processed = false;
    private LocalDateTime receivedAt;

    @PrePersist
    void onCreate() {
        receivedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Long getLiveClassId() { return liveClassId; }
    public void setLiveClassId(Long liveClassId) { this.liveClassId = liveClassId; }
    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }
}