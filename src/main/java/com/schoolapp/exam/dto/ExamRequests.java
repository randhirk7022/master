package com.schoolapp.exam.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExamRequests {

    public static class CreateExamRequest {
        @NotBlank
        private String name;
        @NotNull
        private Long academicYearId;
        @NotNull
        private Long classId;
        private LocalDate startDate;
        private LocalDate endDate;

        public String getName() { return name; }
        public Long getAcademicYearId() { return academicYearId; }
        public Long getClassId() { return classId; }
        public LocalDate getStartDate() { return startDate; }
        public LocalDate getEndDate() { return endDate; }
    }

    public static class AddExamSubjectRequest {
        @NotNull
        private Long subjectId;
        @NotNull
        private BigDecimal maxMarks;
        @NotNull
        private BigDecimal passingMarks;

        public Long getSubjectId() { return subjectId; }
        public BigDecimal getMaxMarks() { return maxMarks; }
        public BigDecimal getPassingMarks() { return passingMarks; }
    }

    public static class EnterMarksRequest {
        @NotNull
        private Long subjectId;

        @Valid
        @NotEmpty
        private List<MarkRecordRequest> records;

        public Long getSubjectId() { return subjectId; }
        public List<MarkRecordRequest> getRecords() { return records; }
    }

    public static class MarkRecordRequest {
        @NotNull
        private Long studentId;
        @NotNull
        private BigDecimal marksObtained;
        private String remarks;

        public Long getStudentId() { return studentId; }
        public BigDecimal getMarksObtained() { return marksObtained; }
        public String getRemarks() { return remarks; }
    }
}