package com.schoolapp.teacher.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class CreateTeacherRequest {

    @NotBlank
    private String employeeCode;

    @NotBlank
    private String firstName;

    private String lastName;
    private String email;
    private String phoneNumber;
    private String qualification;
    private LocalDate joiningDate;
    private Long userId;

    public String getEmployeeCode() {
        return employeeCode;
    }

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

    public Long getUserId() {
        return userId;
    }
}