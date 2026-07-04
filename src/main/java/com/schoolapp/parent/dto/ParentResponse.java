package com.schoolapp.parent.dto;

import com.schoolapp.parent.entity.NotificationPreference;

public class ParentResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private String whatsappNumber;
    private String email;
    private String address;
    private Long userId;
    private NotificationPreference notificationPreference;

    public ParentResponse(
            Long id,
            String firstName,
            String lastName,
            String phoneNumber,
            String whatsappNumber,
            String email,
            String address,
            Long userId,
            NotificationPreference notificationPreference
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + (lastName != null ? " " + lastName : "");
        this.phoneNumber = phoneNumber;
        this.whatsappNumber = whatsappNumber;
        this.email = email;
        this.address = address;
        this.userId = userId;
        this.notificationPreference = notificationPreference;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWhatsappNumber() {
        return whatsappNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Long getUserId() {
        return userId;
    }

    public NotificationPreference getNotificationPreference() {
        return notificationPreference;
    }
}