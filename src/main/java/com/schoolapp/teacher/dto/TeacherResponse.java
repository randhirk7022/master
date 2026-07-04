package com.schoolapp.teacher.dto;

import com.schoolapp.teacher.entity.TeacherStatus;

import java.time.LocalDate;

public class TeacherResponse {

    private Long id;
    private String employeeCode;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String qualification;
    private LocalDate joiningDate;
    private TeacherStatus status;
    private Long userId;

    public TeacherResponse(
            Long id,
            String employeeCode,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String qualification,
            LocalDate joiningDate,
            TeacherStatus status,
            Long userId
    ) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + (lastName != null ? " " + lastName : "");
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.qualification = qualification;
        this.joiningDate = joiningDate;
        this.status = status;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeCode() {
        return employeeCode;
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