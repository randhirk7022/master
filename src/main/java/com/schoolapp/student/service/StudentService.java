package com.schoolapp.student.service;

import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.student.dto.*;
import com.schoolapp.student.entity.*;
import com.schoolapp.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponse createStudent(CreateStudentRequest request) {
        if (studentRepository.existsByAdmissionNumber(request.getAdmissionNumber())) {
            throw new RuntimeException("Admission number already exists");
        }

        Student student = new Student();
        student.setAdmissionNumber(request.getAdmissionNumber());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setClassId(request.getClassId());
        student.setSectionId(request.getSectionId());
        student.setRollNumber(request.getRollNumber());
        student.setAdmissionDate(request.getAdmissionDate());
        student.setUserId(request.getUserId());

        return toResponse(studentRepository.save(student));
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public StudentResponse getStudentById(Long id) {
        return toResponse(findStudent(id));
    }

    public StudentResponse updateStudent(Long id, UpdateStudentRequest request) {
        Student student = findStudent(id);

        if (request.getFirstName() != null) {
            student.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            student.setLastName(request.getLastName());
        }

        if (request.getDateOfBirth() != null) {
            student.setDateOfBirth(request.getDateOfBirth());
        }

        if (request.getGender() != null) {
            student.setGender(request.getGender());
        }

        if (request.getAddress() != null) {
            student.setAddress(request.getAddress());
        }

        if (request.getClassId() != null) {
            student.setClassId(request.getClassId());
        }

        if (request.getSectionId() != null) {
            student.setSectionId(request.getSectionId());
        }

        if (request.getRollNumber() != null) {
            student.setRollNumber(request.getRollNumber());
        }

        if (request.getAdmissionDate() != null) {
            student.setAdmissionDate(request.getAdmissionDate());
        }

        if (request.getStatus() != null) {
            student.setStatus(request.getStatus());
        }

        if (request.getUserId() != null) {
            student.setUserId(request.getUserId());
        }

        return toResponse(studentRepository.save(student));
    }

    public void deleteStudent(Long id) {
        Student student = findStudent(id);
        studentRepository.delete(student);
    }

    public StudentResponse deactivateStudent(Long id) {
        Student student = findStudent(id);
        student.setStatus(StudentStatus.INACTIVE);
        return toResponse(studentRepository.save(student));
    }

    private Student findStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    private StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getAdmissionNumber(),
                student.getFirstName(),
                student.getLastName(),
                student.getDateOfBirth(),
                student.getGender(),
                student.getAddress(),
                student.getClassId(),
                student.getSectionId(),
                student.getRollNumber(),
                student.getAdmissionDate(),
                student.getStatus(),
                student.getUserId()
        );
    }
}