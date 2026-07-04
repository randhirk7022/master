package com.schoolapp.exam.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.exam.dto.ExamRequests.*;
import com.schoolapp.exam.dto.ExamResponses.*;
import com.schoolapp.exam.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping("/exams")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<ExamResponse> createExam(@Valid @RequestBody CreateExamRequest request) {
        return ApiResponse.success("Exam created successfully", examService.createExam(request));
    }

    @GetMapping("/exams")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<ExamResponse>> getExams(
            @RequestParam(required = false) Long classId,
            @RequestParam(required = false) Long academicYearId
    ) {
        return ApiResponse.success("Exams fetched successfully", examService.getExams(classId, academicYearId));
    }

    @GetMapping("/exams/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<ExamResponse> getExamById(@PathVariable Long id) {
        return ApiResponse.success("Exam fetched successfully", examService.getExamById(id));
    }

    @PostMapping("/exams/{id}/subjects")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<ExamSubjectResponse> addSubjectToExam(
            @PathVariable Long id,
            @Valid @RequestBody AddExamSubjectRequest request
    ) {
        return ApiResponse.success("Exam subject added successfully", examService.addSubjectToExam(id, request));
    }

    @GetMapping("/exams/{id}/subjects")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<ExamSubjectResponse>> getExamSubjects(@PathVariable Long id) {
        return ApiResponse.success("Exam subjects fetched successfully", examService.getExamSubjects(id));
    }

    @PostMapping("/exams/{id}/marks")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER')")
    public ApiResponse<List<MarkResponse>> enterMarks(
            @PathVariable Long id,
            @Valid @RequestBody EnterMarksRequest request
    ) {
        return ApiResponse.success("Marks saved successfully", examService.enterMarks(id, request));
    }

    @PostMapping("/exams/{id}/publish")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL')")
    public ApiResponse<ExamResponse> publishExam(@PathVariable Long id) {
        return ApiResponse.success("Exam published successfully", examService.publishExam(id));
    }

    @GetMapping("/students/{studentId}/results")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'TEACHER', 'STUDENT', 'PARENT')")
    public ApiResponse<StudentResultResponse> getStudentResults(@PathVariable Long studentId) {
        return ApiResponse.success("Student results fetched successfully", examService.getStudentResults(studentId));
    }
}