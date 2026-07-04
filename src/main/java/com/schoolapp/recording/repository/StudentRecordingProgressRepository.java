package com.schoolapp.recording.repository;

import com.schoolapp.recording.entity.StudentRecordingProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRecordingProgressRepository extends JpaRepository<StudentRecordingProgress, Long> {
    Optional<StudentRecordingProgress> findByRecordedClassIdAndStudentId(Long recordedClassId, Long studentId);
    List<StudentRecordingProgress> findByStudentId(Long studentId);
    List<StudentRecordingProgress> findByRecordedClassId(Long recordedClassId);
}