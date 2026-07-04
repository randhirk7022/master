package com.schoolapp.exam.dto;

import com.schoolapp.exam.entity.ExamStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExamResponses {

    public static class ExamResponse {
        private Long id;
        private String name;
        private Long academicYearId;
        private Long classId;
        private LocalDate startDate;
        private LocalDate endDate;
        private ExamStatus status;

        public ExamResponse(Long id, String name, Long academicYearId, Long classId,
                            LocalDate startDate, LocalDate endDate, ExamStatus status) {
            this.id = id;
            this.name = name;
            this.academicYearId = academicYearId;
            this.classId = classId;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = status;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public Long getAcademicYearId() { return academicYearId; }
        public Long getClassId() { return classId; }
        public LocalDate getStartDate() { return startDate; }
        public LocalDate getEndDate() { return endDate; }
        public ExamStatus getStatus() { return status; }
    }

    public static class ExamSubjectResponse {
        private Long id;
        private Long examId;
        private Long subjectId;
        private BigDecimal maxMarks;
        private BigDecimal passingMarks;

        public ExamSubjectResponse(Long id, Long examId, Long subjectId, BigDecimal maxMarks, BigDecimal passingMarks) {
            this.id = id;
            this.examId = examId;
            this.subjectId = subjectId;
            this.maxMarks = maxMarks;
            this.passingMarks = passingMarks;
        }

        public Long getId() { return id; }
        public Long getExamId() { return examId; }
        public Long getSubjectId() { return subjectId; }
        public BigDecimal getMaxMarks() { return maxMarks; }
        public BigDecimal getPassingMarks() { return passingMarks; }
    }

    public static class MarkResponse {
        private Long id;
        private Long examId;
        private Long subjectId;
        private Long studentId;
        private BigDecimal marksObtained;
        private String grade;
        private String remarks;

        public MarkResponse(Long id, Long examId, Long subjectId, Long studentId,
                            BigDecimal marksObtained, String grade, String remarks) {
            this.id = id;
            this.examId = examId;
            this.subjectId = subjectId;
            this.studentId = studentId;
            this.marksObtained = marksObtained;
            this.grade = grade;
            this.remarks = remarks;
        }

        public Long getId() { return id; }
        public Long getExamId() { return examId; }
        public Long getSubjectId() { return subjectId; }
        public Long getStudentId() { return studentId; }
        public BigDecimal getMarksObtained() { return marksObtained; }
        public String getGrade() { return grade; }
        public String getRemarks() { return remarks; }
    }

    public static class StudentResultResponse {
        private Long studentId;
        private List<MarkResponse> marks;

        public StudentResultResponse(Long studentId, List<MarkResponse> marks) {
            this.studentId = studentId;
            this.marks = marks;
        }

        public Long getStudentId() { return studentId; }
        public List<MarkResponse> getMarks() { return marks; }
    }
}