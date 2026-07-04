package com.schoolapp.recording.service;

import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.recording.dto.RecordingRequests.*;
import com.schoolapp.recording.dto.RecordingResponses.*;
import com.schoolapp.recording.entity.*;
import com.schoolapp.recording.repository.*;
import com.schoolapp.recording.storage.RecordingStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schoolapp.audit.entity.AuditAction;
import com.schoolapp.audit.service.AuditLogService;
import com.schoolapp.common.security.SecurityUtils;
import com.schoolapp.notification.service.NotificationTriggerService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecordedClassService {

    private final RecordedClassRepository recordedClassRepository;
    private final RecordingAccessLogRepository accessLogRepository;
    private final StudentRecordingProgressRepository progressRepository;
    private final RecordingStorageService storageService;
    private final AuditLogService auditLogService;
    private final NotificationTriggerService notificationTriggerService;

    public RecordedClassService(RecordedClassRepository recordedClassRepository,
                                RecordingAccessLogRepository accessLogRepository,
                                StudentRecordingProgressRepository progressRepository,
                                RecordingStorageService storageService,
                                AuditLogService auditLogService,
                                NotificationTriggerService notificationTriggerService) {
        this.recordedClassRepository = recordedClassRepository;
        this.accessLogRepository = accessLogRepository;
        this.progressRepository = progressRepository;
        this.storageService = storageService;
        this.auditLogService = auditLogService;
        this.notificationTriggerService = notificationTriggerService;
    }

    @Transactional
    public RecordedClassResponse createRecordedClass(CreateRecordedClassRequest request) {
        RecordedClass recordedClass = new RecordedClass();
        recordedClass.setTitle(request.getTitle());
        recordedClass.setDescription(request.getDescription());
        recordedClass.setLiveClassId(request.getLiveClassId());
        recordedClass.setAcademicYearId(request.getAcademicYearId());
        recordedClass.setClassId(request.getClassId());
        recordedClass.setSectionId(request.getSectionId());
        recordedClass.setSubjectId(request.getSubjectId());
        recordedClass.setTeacherId(request.getTeacherId());
        recordedClass.setStorageProvider(request.getStorageProvider());
        recordedClass.setRecordingUrl(request.getRecordingUrl());
        recordedClass.setStorageKey(request.getStorageKey() != null ? request.getStorageKey() : "recording-" + System.currentTimeMillis());
        recordedClass.setThumbnailUrl(request.getThumbnailUrl());
        recordedClass.setDurationInMinutes(request.getDurationInMinutes());
        recordedClass.setFileSize(request.getFileSize());
        recordedClass.setAvailableFrom(request.getAvailableFrom());
        recordedClass.setAvailableUntil(request.getAvailableUntil());
        recordedClass.setStatus(RecordedClassStatus.AVAILABLE);

        RecordedClass saved = recordedClassRepository.save(recordedClass);

        notificationTriggerService.sendRecordingAvailable(
                saved.getTeacherId(),
                saved.getTitle()
        );

        return toResponse(saved);
    }

    public List<RecordedClassResponse> getRecordedClasses(Long classId, Long sectionId, Long teacherId) {
        if (classId != null && sectionId != null) {
            return recordedClassRepository.findByClassIdAndSectionId(classId, sectionId)
                    .stream().map(this::toResponse).toList();
        }

        if (teacherId != null) {
            return recordedClassRepository.findByTeacherId(teacherId)
                    .stream().map(this::toResponse).toList();
        }

        return recordedClassRepository.findAll().stream().map(this::toResponse).toList();
    }

    public RecordedClassResponse getRecordedClassById(Long id) {
        return toResponse(findRecordedClass(id));
    }

    public RecordedClassResponse updateRecordedClass(Long id, UpdateRecordedClassRequest request) {
        RecordedClass recordedClass = findRecordedClass(id);

        if (request.getTitle() != null) {
            recordedClass.setTitle(request.getTitle());
        }

        if (request.getDescription() != null) {
            recordedClass.setDescription(request.getDescription());
        }

        if (request.getThumbnailUrl() != null) {
            recordedClass.setThumbnailUrl(request.getThumbnailUrl());
        }

        if (request.getAvailableFrom() != null) {
            recordedClass.setAvailableFrom(request.getAvailableFrom());
        }

        if (request.getAvailableUntil() != null) {
            recordedClass.setAvailableUntil(request.getAvailableUntil());
        }

        return toResponse(recordedClassRepository.save(recordedClass));
    }

    public RecordedClassResponse deleteRecordedClass(Long id) {
        RecordedClass recordedClass = findRecordedClass(id);
        recordedClass.setStatus(RecordedClassStatus.DELETED);

        if (recordedClass.getStorageKey() != null) {
            storageService.deleteRecording(recordedClass.getStorageKey());
        }

        return toResponse(recordedClassRepository.save(recordedClass));
    }

    public List<RecordedClassResponse> getStudentRecordedClasses(Long studentId, Long classId, Long sectionId) {
        if (classId == null || sectionId == null) {
            return recordedClassRepository.findAll()
                    .stream()
                    .filter(recordedClass -> recordedClass.getStatus() == RecordedClassStatus.AVAILABLE)
                    .map(this::toResponse)
                    .toList();
        }

        return recordedClassRepository.findByClassIdAndSectionId(classId, sectionId)
                .stream()
                .filter(recordedClass -> recordedClass.getStatus() == RecordedClassStatus.AVAILABLE)
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public PlaybackUrlResponse getPlaybackUrl(Long id, Long studentId, String ipAddress, String deviceInfo) {
        RecordedClass recordedClass = findRecordedClass(id);
        validatePlayable(recordedClass);

        RecordingAccessLog accessLog = new RecordingAccessLog();
        accessLog.setRecordedClassId(id);
        accessLog.setStudentId(studentId);
        accessLog.setIpAddress(ipAddress);
        accessLog.setDeviceInfo(deviceInfo);
        accessLogRepository.save(accessLog);
        
        auditLogService.log(
                AuditAction.RECORDING_ACCESSED,
                "RECORDED_CLASS",
                "RecordedClass",
                recordedClass.getId(),
                "Recording accessed by student id: " + studentId,
                null,
                SecurityUtils.getCurrentUsername(),
                ipAddress,
                deviceInfo
        );

        String playbackUrl = storageService.generateSecurePlaybackUrl(
                recordedClass.getStorageKey(),
                id,
                studentId
        );

        return new PlaybackUrlResponse(id, studentId, playbackUrl);
    }

    public StudentRecordingProgressResponse updateProgress(Long id, UpdateProgressRequest request) {
        findRecordedClass(id);

        StudentRecordingProgress progress = progressRepository
                .findByRecordedClassIdAndStudentId(id, request.getStudentId())
                .orElseGet(StudentRecordingProgress::new);

        progress.setRecordedClassId(id);
        progress.setStudentId(request.getStudentId());
        progress.setWatchedDurationInMinutes(request.getWatchedDurationInMinutes());
        progress.setCompleted(request.isCompleted());

        return toProgressResponse(progressRepository.save(progress));
    }

    public List<StudentRecordingProgressResponse> getStudentProgress(Long studentId) {
        return progressRepository.findByStudentId(studentId)
                .stream()
                .map(this::toProgressResponse)
                .toList();
    }

    private void validatePlayable(RecordedClass recordedClass) {
        if (recordedClass.getStatus() != RecordedClassStatus.AVAILABLE) {
            throw new RuntimeException("Recording is not available");
        }

        LocalDateTime now = LocalDateTime.now();

        if (recordedClass.getAvailableFrom() != null && recordedClass.getAvailableFrom().isAfter(now)) {
            throw new RuntimeException("Recording is not available yet");
        }

        if (recordedClass.getAvailableUntil() != null && recordedClass.getAvailableUntil().isBefore(now)) {
            recordedClass.setStatus(RecordedClassStatus.EXPIRED);
            recordedClassRepository.save(recordedClass);
            throw new RuntimeException("Recording has expired");
        }
    }

    private RecordedClass findRecordedClass(Long id) {
        return recordedClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recorded class not found with id: " + id));
    }

    private RecordedClassResponse toResponse(RecordedClass recordedClass) {
        return new RecordedClassResponse(
                recordedClass.getId(),
                recordedClass.getTitle(),
                recordedClass.getLiveClassId(),
                recordedClass.getClassId(),
                recordedClass.getSectionId(),
                recordedClass.getSubjectId(),
                recordedClass.getTeacherId(),
                recordedClass.getRecordingUrl(),
                recordedClass.getThumbnailUrl(),
                recordedClass.getDurationInMinutes(),
                recordedClass.getStatus(),
                recordedClass.getAvailableFrom(),
                recordedClass.getAvailableUntil()
        );
    }

    private StudentRecordingProgressResponse toProgressResponse(StudentRecordingProgress progress) {
        return new StudentRecordingProgressResponse(
                progress.getId(),
                progress.getRecordedClassId(),
                progress.getStudentId(),
                progress.getWatchedDurationInMinutes(),
                progress.isCompleted(),
                progress.getLastWatchedAt()
        );
    }
}