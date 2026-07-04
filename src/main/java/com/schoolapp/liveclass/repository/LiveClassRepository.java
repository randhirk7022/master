package com.schoolapp.liveclass.repository;

import com.schoolapp.liveclass.entity.LiveClass;
import com.schoolapp.liveclass.entity.LiveClassStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LiveClassRepository extends JpaRepository<LiveClass, Long> {
    List<LiveClass> findByClassIdAndSectionId(Long classId, Long sectionId);
    List<LiveClass> findByTeacherId(Long teacherId);
    List<LiveClass> findByStatus(LiveClassStatus status);
}