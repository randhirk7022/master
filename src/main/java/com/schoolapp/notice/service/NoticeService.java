package com.schoolapp.notice.service;

import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.notice.dto.NoticeRequests.*;
import com.schoolapp.notice.dto.NoticeResponses.*;
import com.schoolapp.notice.entity.*;
import com.schoolapp.notice.repository.*;
import com.schoolapp.notification.service.NotificationTriggerService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schoolapp.audit.entity.AuditAction;
import com.schoolapp.audit.service.AuditLogService;
import com.schoolapp.common.security.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeRecipientRepository recipientRepository;
    private final AuditLogService auditLogService;
    private final NotificationTriggerService notificationTriggerService;

    public NoticeService(NoticeRepository noticeRepository, NoticeRecipientRepository recipientRepository,
    		AuditLogService auditLogService, NotificationTriggerService notificationTriggerService) {
        this.noticeRepository = noticeRepository;
        this.recipientRepository = recipientRepository;
        this.auditLogService = auditLogService;
        this.notificationTriggerService = notificationTriggerService;
    }

    @Transactional
    public NoticeResponse createNotice(CreateNoticeRequest request) {
        Notice notice = new Notice();
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setTargetType(request.getTargetType());
        notice.setTargetRole(request.getTargetRole());
        notice.setClassId(request.getClassId());
        notice.setSectionId(request.getSectionId());
        notice.setPublishedByUserId(request.getPublishedByUserId());

        Notice savedNotice = noticeRepository.save(notice);

        createRecipients(savedNotice.getId(), request.getRecipientUserIds());
        
        // Audit Notice created
        auditLogService.log(
                AuditAction.CREATE,
                "NOTICE",
                "Notice",
                savedNotice.getId(),
                "Notice created: " + savedNotice.getTitle(),
                null,
                SecurityUtils.getCurrentUsername(),
                null,
                null
        );

        return toResponse(savedNotice);
    }

    public List<NoticeResponse> getNotices(NoticeStatus status, Long userId) {
        if (userId != null) {
            return recipientRepository.findByUserId(userId)
                    .stream()
                    .map(recipient -> noticeRepository.findById(recipient.getNoticeId())
                            .orElseThrow(() -> new ResourceNotFoundException("Notice not found")))
                    .map(this::toResponse)
                    .toList();
        }

        if (status != null) {
            return noticeRepository.findByStatus(status)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        return noticeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public NoticeResponse getNoticeById(Long id) {
        return toResponse(findNotice(id));
    }

    @Transactional
    public NoticeResponse updateNotice(Long id, UpdateNoticeRequest request) {
        Notice notice = findNotice(id);

        if (notice.getStatus() == NoticeStatus.PUBLISHED) {
            throw new RuntimeException("Published notice cannot be edited");
        }

        if (request.getTitle() != null) {
            notice.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            notice.setContent(request.getContent());
        }

        if (request.getTargetType() != null) {
            notice.setTargetType(request.getTargetType());
        }

        if (request.getTargetRole() != null) {
            notice.setTargetRole(request.getTargetRole());
        }

        if (request.getClassId() != null) {
            notice.setClassId(request.getClassId());
        }

        if (request.getSectionId() != null) {
            notice.setSectionId(request.getSectionId());
        }

        Notice savedNotice = noticeRepository.save(notice);

        if (request.getRecipientUserIds() != null) {
            recipientRepository.findByNoticeId(id).forEach(recipientRepository::delete);
            createRecipients(id, request.getRecipientUserIds());
        }

        return toResponse(savedNotice);
    }

    public NoticeResponse publishNotice(Long id) {
        Notice notice = findNotice(id);
        notice.setStatus(NoticeStatus.PUBLISHED);
        notice.setPublishedAt(LocalDateTime.now());

        Notice savedNotice = noticeRepository.save(notice);
        
        List<Long> recipientUserIds = recipientRepository.findByNoticeId(savedNotice.getId())
                .stream()
                .map(NoticeRecipient::getUserId)
                .toList();

        notificationTriggerService.sendNoticePublished(recipientUserIds, savedNotice.getTitle());

        // AuditLog Notice published
        auditLogService.log(
                AuditAction.PUBLISH,
                "NOTICE",
                "Notice",
                savedNotice.getId(),
                "Notice published: " + savedNotice.getTitle(),
                null,
                SecurityUtils.getCurrentUsername(),
                null,
                null
        );

        return toResponse(savedNotice);
    }

    public NoticeResponse archiveNotice(Long id) {
        Notice notice = findNotice(id);
        notice.setStatus(NoticeStatus.ARCHIVED);
        return toResponse(noticeRepository.save(notice));
    }

    public NoticeRecipientResponse markAsRead(Long noticeId, Long userId) {
        NoticeRecipient recipient = recipientRepository.findByNoticeIdAndUserId(noticeId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Notice recipient not found"));

        recipient.setReadStatus(true);
        recipient.setReadAt(LocalDateTime.now());

        return toRecipientResponse(recipientRepository.save(recipient));
    }

    private void createRecipients(Long noticeId, List<Long> recipientUserIds) {
        if (recipientUserIds == null || recipientUserIds.isEmpty()) {
            return;
        }

        List<NoticeRecipient> recipients = recipientUserIds.stream()
                .filter(userId -> !recipientRepository.existsByNoticeIdAndUserId(noticeId, userId))
                .map(userId -> {
                    NoticeRecipient recipient = new NoticeRecipient();
                    recipient.setNoticeId(noticeId);
                    recipient.setUserId(userId);
                    return recipient;
                })
                .toList();

        recipientRepository.saveAll(recipients);
    }

    private Notice findNotice(Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notice not found with id: " + id));
    }

    private NoticeResponse toResponse(Notice notice) {
        List<NoticeRecipientResponse> recipients = recipientRepository.findByNoticeId(notice.getId())
                .stream()
                .map(this::toRecipientResponse)
                .toList();

        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getTargetType(),
                notice.getTargetRole(),
                notice.getClassId(),
                notice.getSectionId(),
                notice.getPublishedByUserId(),
                notice.getPublishedAt(),
                notice.getStatus(),
                recipients
        );
    }

    private NoticeRecipientResponse toRecipientResponse(NoticeRecipient recipient) {
        return new NoticeRecipientResponse(
                recipient.getId(),
                recipient.getUserId(),
                recipient.isReadStatus(),
                recipient.getReadAt()
        );
    }
}