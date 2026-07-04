package com.schoolapp.academic.repository;

import com.schoolapp.academic.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    boolean existsByNameAndAcademicYearId(String name, Long academicYearId);
    List<SchoolClass> findByAcademicYearId(Long academicYearId);
}