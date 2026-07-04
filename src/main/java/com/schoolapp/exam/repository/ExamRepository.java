package com.schoolapp.exam.repository;

import com.schoolapp.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByClassId(Long classId);
    List<Exam> findByAcademicYearId(Long academicYearId);
}