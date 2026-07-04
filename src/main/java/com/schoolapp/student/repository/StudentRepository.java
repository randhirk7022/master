package com.schoolapp.student.repository;

import com.schoolapp.student.entity.Student;
import com.schoolapp.student.entity.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByAdmissionNumber(String admissionNumber);

    boolean existsByAdmissionNumber(String admissionNumber);

    List<Student> findByClassIdAndSectionId(Long classId, Long sectionId);

    List<Student> findByStatus(StudentStatus status);
}