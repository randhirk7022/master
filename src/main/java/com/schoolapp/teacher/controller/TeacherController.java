package com.schoolapp.teacher.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.teacher.dto.*;
import com.schoolapp.teacher.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<TeacherResponse> createTeacher(
            @Valid @RequestBody CreateTeacherRequest request
    ) {
        return ApiResponse.success(
                "Teacher created successfully",
                teacherService.createTeacher(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<TeacherResponse>> getAllTeachers() {
        return ApiResponse.success(
                "Teachers fetched successfully",
                teacherService.getAllTeachers()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<TeacherResponse> getTeacherById(@PathVariable Long id) {
        return ApiResponse.success(
                "Teacher fetched successfully",
                teacherService.getTeacherById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<TeacherResponse> updateTeacher(
            @PathVariable Long id,
            @RequestBody UpdateTeacherRequest request
    ) {
        return ApiResponse.success(
                "Teacher updated successfully",
                teacherService.updateTeacher(id, request)
        );
    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<TeacherResponse> deactivateTeacher(@PathVariable Long id) {
        return ApiResponse.success(
                "Teacher deactivated successfully",
                teacherService.deactivateTeacher(id)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
        return ApiResponse.success("Teacher deleted successfully", null);
    }
}