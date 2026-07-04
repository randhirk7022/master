package com.schoolapp.exam.repository;

import com.schoolapp.exam.entity.ExamSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExamSubjectRepository extends JpaRepository<ExamSubject, Long> {
    boolean existsByExamIdAndSubjectId(Long examId, Long subjectId);
    List<ExamSubject> findByExamId(Long examId);
    Optional<ExamSubject> findByExamIdAndSubjectId(Long examId, Long subjectId);
}