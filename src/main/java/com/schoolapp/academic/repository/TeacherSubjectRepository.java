package com.schoolapp.academic.repository;

import com.schoolapp.academic.entity.TeacherSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherSubjectRepository extends JpaRepository<TeacherSubject, Long> {
    List<TeacherSubject> findByTeacherId(Long teacherId);
    List<TeacherSubject> findByClassIdAndSectionId(Long classId, Long sectionId);
}