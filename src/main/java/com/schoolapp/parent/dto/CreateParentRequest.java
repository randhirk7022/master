package com.schoolapp.parent.dto;

import com.schoolapp.parent.entity.NotificationPreference;
import jakarta.validation.constraints.NotBlank;

public class CreateParentRequest {

    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    private String phoneNumber;

    private String whatsappNumber;
    private String email;
    private String address;
    private Long userId;
    private NotificationPreference notificationPreference;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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