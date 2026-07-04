package com.schoolapp.teacher.repository;

import com.schoolapp.teacher.entity.Teacher;
import com.schoolapp.teacher.entity.TeacherStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmployeeCode(String employeeCode);

    boolean existsByEmployeeCode(String employeeCode);

    List<Teacher> findByStatus(TeacherStatus status);
}