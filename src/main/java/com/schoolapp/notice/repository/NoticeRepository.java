package com.schoolapp.notice.repository;

import com.schoolapp.notice.entity.Notice;
import com.schoolapp.notice.entity.NoticeStatus;
import com.schoolapp.notice.entity.NoticeTargetType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByStatus(NoticeStatus status);
    List<Notice> findByTargetType(NoticeTargetType targetType);
    List<Notice> findByClassId(Long classId);
    List<Notice> findBySectionId(Long sectionId);
    List<Notice> findByTargetRole(String targetRole);
}