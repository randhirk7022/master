package com.schoolapp.teacher.dto;

import com.schoolapp.teacher.entity.TeacherStatus;

import java.time.LocalDate;

public class UpdateTeacherRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String qualification;
    private LocalDate joiningDate;
    private TeacherStatus status;
    private Long userId;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getQualification() {
        return qualification;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public TeacherStatus getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }
}