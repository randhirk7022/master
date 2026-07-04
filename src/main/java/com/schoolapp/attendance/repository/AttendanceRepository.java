package com.schoolapp.attendance.repository;

import com.schoolapp.attendance.entity.Attendance;
import com.schoolapp.attendance.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);

    List<Attendance> findByClassIdAndSectionIdAndAttendanceDate(
            Long classId,
            Long sectionId,
            LocalDate attendanceDate
    );

    List<Attendance> findByStudentIdAndAttendanceDateBetween(
            Long studentId,
            LocalDate startDate,
            LocalDate endDate
    );

    long countByStudentIdAndStatusAndAttendanceDateBetween(
            Long studentId,
            AttendanceStatus status,
            LocalDate startDate,
            LocalDate endDate
    );
}