package com.schoolapp.exam.repository;

import com.schoolapp.exam.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findByExamIdAndSubjectIdAndStudentId(Long examId, Long subjectId, Long studentId);
    List<Mark> findByExamIdAndStudentId(Long examId, Long studentId);
    List<Mark> findByStudentId(Long studentId);
}