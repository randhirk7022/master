package com.schoolapp.recording.repository;

import com.schoolapp.recording.entity.RecordedClass;
import com.schoolapp.recording.entity.RecordedClassStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecordedClassRepository extends JpaRepository<RecordedClass, Long> {
    List<RecordedClass> findByClassIdAndSectionId(Long classId, Long sectionId);
    List<RecordedClass> findByTeacherId(Long teacherId);
    List<RecordedClass> findByLiveClassId(Long liveClassId);
    List<RecordedClass> findByStatus(RecordedClassStatus status);
}