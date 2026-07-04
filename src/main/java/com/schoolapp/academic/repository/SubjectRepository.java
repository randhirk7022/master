package com.schoolapp.academic.repository;

import com.schoolapp.academic.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    boolean existsByCode(String code);
}