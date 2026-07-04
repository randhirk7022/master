package com.schoolapp.student.dto;

import com.schoolapp.student.entity.Gender;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class CreateStudentRequest {

    @NotBlank
    private String admissionNumber;

    @NotBlank
    private String firstName;

    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String address;
    private Long classId;
    private Long sectionId;
    private String rollNumber;
    private LocalDate admissionDate;
    private Long userId;

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public Long getClassId() {
        return classId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public Long getUserId() {
        return userId;
    }
} 	