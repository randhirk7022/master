package com.schoolapp.parent.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.parent.dto.*;
import com.schoolapp.parent.service.ParentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parents")
public class ParentController {

    private final ParentService parentService;

    public ParentController(ParentService parentService) {
        this.parentService = parentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<ParentResponse> createParent(
            @Valid @RequestBody CreateParentRequest request
    ) {
        return ApiResponse.success(
                "Parent created successfully",
                parentService.createParent(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<ParentResponse>> getAllParents() {
        return ApiResponse.success(
                "Parents fetched successfully",
                parentService.getAllParents()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'PARENT')")
    public ApiResponse<ParentResponse> getParentById(@PathVariable Long id) {
        return ApiResponse.success(
                "Parent fetched successfully",
                parentService.getParentById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<ParentResponse> updateParent(
            @PathVariable Long id,
            @RequestBody UpdateParentRequest request
    ) {
        return ApiResponse.success(
                "Parent updated successfully",
                parentService.updateParent(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteParent(@PathVariable Long id) {
        parentService.deleteParent(id);
        return ApiResponse.success("Parent deleted successfully", null);
    }

    @PostMapping("/link-student")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<StudentParentResponse> linkStudentParent(
            @Valid @RequestBody LinkStudentParentRequest request
    ) {
        return ApiResponse.success(
                "Student and parent linked successfully",
                parentService.linkStudentParent(request)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<StudentParentResponse>> getParentsByStudentId(
            @PathVariable Long studentId
    ) {
        return ApiResponse.success(
                "Student parent links fetched successfully",
                parentService.getParentsByStudentId(studentId)
        );
    }

    @GetMapping("/{parentId}/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'PARENT')")
    public ApiResponse<List<StudentParentResponse>> getStudentsByParentId(
            @PathVariable Long parentId
    ) {
        return ApiResponse.success(
                "Parent student links fetched successfully",
                parentService.getStudentsByParentId(parentId)
        );
    }
}