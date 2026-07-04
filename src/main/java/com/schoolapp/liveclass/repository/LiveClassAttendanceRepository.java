package com.schoolapp.liveclass.repository;

import com.schoolapp.liveclass.entity.LiveClassAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LiveClassAttendanceRepository extends JpaRepository<LiveClassAttendance, Long> {
    List<LiveClassAttendance> findByLiveClassId(Long liveClassId);
    List<LiveClassAttendance> findByStudentId(Long studentId);
}