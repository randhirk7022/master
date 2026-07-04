package com.schoolapp.fee.repository;

import com.schoolapp.fee.entity.FeeStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeeStructureRepository extends JpaRepository<FeeStructure, Long> {
    List<FeeStructure> findByClassId(Long classId);
    List<FeeStructure> findByAcademicYearIdAndClassId(Long academicYearId, Long classId);
}