package com.schoolapp.academic.controller;

import com.schoolapp.academic.dto.AcademicRequests.*;
import com.schoolapp.academic.entity.*;
import com.schoolapp.academic.service.AcademicService;
import com.schoolapp.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/academic")
public class AcademicController {

    private final AcademicService academicService;

    public AcademicController(AcademicService academicService) {
        this.academicService = academicService;
    }

    @PostMapping("/years")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<AcademicYear> createAcademicYear(
            @Valid @RequestBody CreateAcademicYearRequest request
    ) {
        return ApiResponse.success(
                "Academic year created successfully",
                academicService.createAcademicYear(request)
        );
    }

    @GetMapping("/years")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<AcademicYear>> getAcademicYears() {
        return ApiResponse.success(
                "Academic years fetched successfully",
                academicService.getAcademicYears()
        );
    }

    @PostMapping("/classes")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<SchoolClass> createClass(
            @Valid @RequestBody CreateClassRequest request
    ) {
        return ApiResponse.success(
                "Class created successfully",
                academicService.createClass(request)
        );
    }

    @GetMapping("/classes")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<SchoolClass>> getClasses(
            @RequestParam(required = false) Long academicYearId
    ) {
        return ApiResponse.success(
                "Classes fetched successfully",
                academicService.getClasses(academicYearId)
        );
    }

    @PostMapping("/sections")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<Section> createSection(
            @Valid @RequestBody CreateSectionRequest request
    ) {
        return ApiResponse.success(
                "Section created successfully",
                academicService.createSection(request)
        );
    }

    @GetMapping("/sections")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<Section>> getSections(
            @RequestParam(required = false) Long classId
    ) {
        return ApiResponse.success(
                "Sections fetched successfully",
                academicService.getSections(classId)
        );
    }

    @PostMapping("/subjects")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<Subject> createSubject(
            @Valid @RequestBody CreateSubjectRequest request
    ) {
        return ApiResponse.success(
                "Subject created successfully",
                academicService.createSubject(request)
        );
    }

    @GetMapping("/subjects")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<Subject>> getSubjects() {
        return ApiResponse.success(
                "Subjects fetched successfully",
                academicService.getSubjects()
        );
    }

    @PostMapping("/class-subjects")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<ClassSubject> assignSubjectToClass(
            @Valid @RequestBody AssignSubjectToClassRequest request
    ) {
        return ApiResponse.success(
                "Subject assigned to class successfully",
                academicService.assignSubjectToClass(request)
        );
    }

    @GetMapping("/class-subjects/{classId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<ClassSubject>> getClassSubjects(@PathVariable Long classId) {
        return ApiResponse.success(
                "Class subjects fetched successfully",
                academicService.getClassSubjects(classId)
        );
    }

    @PostMapping("/teacher-subjects")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<TeacherSubject> assignSubjectToTeacher(
            @Valid @RequestBody AssignSubjectToTeacherRequest request
    ) {
        return ApiResponse.success(
                "Subject assigned to teacher successfully",
                academicService.assignSubjectToTeacher(request)
        );
    }

    @GetMapping("/teacher-subjects/{teacherId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<TeacherSubject>> getTeacherSubjects(@PathVariable Long teacherId) {
        return ApiResponse.success(
                "Teacher subjects fetched successfully",
                academicService.getTeacherSubjects(teacherId)
        );
    }
}