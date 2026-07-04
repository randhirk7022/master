package com.schoolapp.exam.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "exam_subjects",
        uniqueConstraints = @UniqueConstraint(columnNames = {"exam_id", "subject_id"})
)
public class ExamSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exam_id", nullable = false)
    private Long examId;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(nullable = false)
    private BigDecimal maxMarks;

    @Column(nullable = false)
    private BigDecimal passingMarks;

    public Long getId() { return id; }
    public Long getExamId() { return examId; }
    public void setExamId(Long examId) { this.examId = examId; }
    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
    public BigDecimal getMaxMarks() { return maxMarks; }
    public void setMaxMarks(BigDecimal maxMarks) { this.maxMarks = maxMarks; }
    public BigDecimal getPassingMarks() { return passingMarks; }
    public void setPassingMarks(BigDecimal passingMarks) { this.passingMarks = passingMarks; }
}