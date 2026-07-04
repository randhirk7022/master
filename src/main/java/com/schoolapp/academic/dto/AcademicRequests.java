package com.schoolapp.academic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class AcademicRequests {

    public static class CreateAcademicYearRequest {
        @NotBlank
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean active;

        public String getName() { return name; }
        public LocalDate getStartDate() { return startDate; }
        public LocalDate getEndDate() { return endDate; }
        public boolean isActive() { return active; }
    }

    public static class CreateClassRequest {
        @NotBlank
        private String name;

        @NotNull
        private Long academicYearId;

        public String getName() { return name; }
        public Long getAcademicYearId() { return academicYearId; }
    }

    public static class CreateSectionRequest {
        @NotBlank
        private String name;

        @NotNull
        private Long classId;

        public String getName() { return name; }
        public Long getClassId() { return classId; }
    }

    public static class CreateSubjectRequest {
        @NotBlank
        private String name;

        @NotBlank
        private String code;

        public String getName() { return name; }
        public String getCode() { return code; }
    }

    public static class AssignSubjectToClassRequest {
        @NotNull
        private Long classId;

        @NotNull
        private Long subjectId;

        public Long getClassId() { return classId; }
        public Long getSubjectId() { return subjectId; }
    }

    public static class AssignSubjectToTeacherRequest {
        @NotNull
        private Long teacherId;

        @NotNull
        private Long subjectId;

        @NotNull
        private Long classId;

        private Long sectionId;

        public Long getTeacherId() { return teacherId; }
        public Long getSubjectId() { return subjectId; }
        public Long getClassId() { return classId; }
        public Long getSectionId() { return sectionId; }
    }
}