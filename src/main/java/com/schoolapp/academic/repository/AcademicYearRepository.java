package com.schoolapp.academic.repository;

import com.schoolapp.academic.entity.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
    Optional<AcademicYear> findByName(String name);
    boolean existsByName(String name);
}