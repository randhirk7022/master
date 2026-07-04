package com.schoolapp.recording.repository;

import com.schoolapp.recording.entity.RecordingAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecordingAccessLogRepository extends JpaRepository<RecordingAccessLog, Long> {
    List<RecordingAccessLog> findByRecordedClassId(Long recordedClassId);
    List<RecordingAccessLog> findByStudentId(Long studentId);
}