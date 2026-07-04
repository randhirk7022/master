package com.schoolapp.student.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.student.dto.*;
import com.schoolapp.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<StudentResponse> createStudent(
            @Valid @RequestBody CreateStudentRequest request
    ) {
        return ApiResponse.success(
                "Student created successfully",
                studentService.createStudent(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<StudentResponse>> getAllStudents() {
        return ApiResponse.success(
                "Students fetched successfully",
                studentService.getAllStudents()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<StudentResponse> getStudentById(@PathVariable Long id) {
        return ApiResponse.success(
                "Student fetched successfully",
                studentService.getStudentById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<StudentResponse> updateStudent(
            @PathVariable Long id,
            @RequestBody UpdateStudentRequest request
    ) {
        return ApiResponse.success(
                "Student updated successfully",
                studentService.updateStudent(id, request)
        );
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<StudentResponse> deactivateStudent(@PathVariable Long id) {
        return ApiResponse.success(
                "Student deactivated successfully",
                studentService.deactivateStudent(id)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ApiResponse.success("Student deleted successfully", null);
    }
}