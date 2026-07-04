package com.schoolapp.notification.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification_webhook_events")
public class NotificationWebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerName;

    @Column(unique = true)
    private String eventId;

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
    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }
}