package com.schoolapp.notice.repository;

import com.schoolapp.notice.entity.NoticeRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface NoticeRecipientRepository extends JpaRepository<NoticeRecipient, Long> {
    List<NoticeRecipient> findByNoticeId(Long noticeId);
    List<NoticeRecipient> findByUserId(Long userId);
    Optional<NoticeRecipient> findByNoticeIdAndUserId(Long noticeId, Long userId);
    boolean existsByNoticeIdAndUserId(Long noticeId, Long userId);
}