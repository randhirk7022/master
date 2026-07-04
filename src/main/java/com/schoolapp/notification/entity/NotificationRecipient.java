package com.schoolapp.notification.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_recipients")
public class NotificationRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long notificationRequestId;
    private Long recipientUserId;
    private String phoneNumber;
    private String whatsappNumber;

    @Column(length = 2000)
    private String messageBody;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.QUEUED;

    public Long getId() { return id; }
    public Long getNotificationRequestId() { return notificationRequestId; }
    public void setNotificationRequestId(Long notificationRequestId) { this.notificationRequestId = notificationRequestId; }
    public Long getRecipientUserId() { return recipientUserId; }
    public void setRecipientUserId(Long recipientUserId) { this.recipientUserId = recipientUserId; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getWhatsappNumber() { return whatsappNumber; }
    public void setWhatsappNumber(String whatsappNumber) { this.whatsappNumber = whatsappNumber; }
    public String getMessageBody() { return messageBody; }
    public void setMessageBody(String messageBody) { this.messageBody = messageBody; }
    public NotificationStatus getStatus() { return status; }
    public void setStatus(NotificationStatus status) { this.status = status; }
}