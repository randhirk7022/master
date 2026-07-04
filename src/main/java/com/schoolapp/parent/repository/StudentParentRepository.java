package com.schoolapp.parent.repository;

import com.schoolapp.parent.entity.StudentParent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentParentRepository extends JpaRepository<StudentParent, Long> {

    List<StudentParent> findByStudentId(Long studentId);

    List<StudentParent> findByParentId(Long parentId);

    boolean existsByStudentIdAndParentId(Long studentId, Long parentId);
}