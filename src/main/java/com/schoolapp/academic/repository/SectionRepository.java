package com.schoolapp.academic.repository;

import com.schoolapp.academic.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {
    boolean existsByNameAndClassId(String name, Long classId);
    List<Section> findByClassId(Long classId);
}