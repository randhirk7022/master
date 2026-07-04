package com.schoolapp.academic.repository;

import com.schoolapp.academic.entity.ClassSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassSubjectRepository extends JpaRepository<ClassSubject, Long> {
    boolean existsByClassIdAndSubjectId(Long classId, Long subjectId);
    List<ClassSubject> findByClassId(Long classId);
}