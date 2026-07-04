package com.schoolapp.student.dto;

import com.schoolapp.student.entity.Gender;
import com.schoolapp.student.entity.StudentStatus;

import java.time.LocalDate;

public class StudentResponse {

    private Long id;
    private String admissionNumber;
    private String firstName;
    private String lastName;
    private String fullName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String address;
    private Long classId;
    private Long sectionId;
    private String rollNumber;
    private LocalDate admissionDate;
    private StudentStatus status;
    private Long userId;

    public StudentResponse(
            Long id,
            String admissionNumber,
            String firstName,
            String lastName,
            LocalDate dateOfBirth,
            Gender gender,
            String address,
            Long classId,
            Long sectionId,
            String rollNumber,
            LocalDate admissionDate,
            StudentStatus status,
            Long userId
    ) {
        this.id = id;
        this.admissionNumber = admissionNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + (lastName != null ? " " + lastName : "");
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.classId = classId;
        this.sectionId = sectionId;
        this.rollNumber = rollNumber;
        this.admissionDate = admissionDate;
        this.status = status;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
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

    public StudentStatus getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }
}