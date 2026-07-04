package com.schoolapp.liveclass.service;

import com.schoolapp.attendance.entity.AttendanceStatus;
import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.liveclass.dto.LiveClassRequests.*;
import com.schoolapp.liveclass.dto.LiveClassResponses.*;
import com.schoolapp.liveclass.entity.*;
import com.schoolapp.liveclass.provider.*;
import com.schoolapp.liveclass.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schoolapp.audit.entity.AuditAction;
import com.schoolapp.audit.service.AuditLogService;
import com.schoolapp.common.security.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LiveClassService {

    private final LiveClassRepository liveClassRepository;
    private final LiveClassParticipantRepository participantRepository;
    private final LiveClassAttendanceRepository attendanceRepository;
    private final LiveClassProviderEventRepository providerEventRepository;
    private final LiveClassProviderFactory providerFactory;
    private final AuditLogService auditLogService;

    public LiveClassService(LiveClassRepository liveClassRepository,
                            LiveClassParticipantRepository participantRepository,
                            LiveClassAttendanceRepository attendanceRepository,
                            LiveClassProviderEventRepository providerEventRepository,
                            LiveClassProviderFactory providerFactory,
                            AuditLogService auditLogService) {
        this.liveClassRepository = liveClassRepository;
        this.participantRepository = participantRepository;
        this.attendanceRepository = attendanceRepository;
        this.providerEventRepository = providerEventRepository;
        this.providerFactory = providerFactory;
        this.auditLogService = auditLogService;
    }

    @Transactional
    public LiveClassResponse createLiveClass(CreateLiveClassRequest request) {
        LiveClassProviderService provider = providerFactory.getProvider(request.getProviderName());

        LiveClassProviderService.ProviderMeetingResponse providerResponse =
                provider.createMeeting(new LiveClassProviderService.ProviderMeetingRequest(
                        request.getTitle(),
                        request.getScheduledStartTime(),
                        request.getScheduledEndTime(),
                        request.isRecordingEnabled()
                ));

        LiveClass liveClass = new LiveClass();
        liveClass.setTitle(request.getTitle());
        liveClass.setDescription(request.getDescription());
        liveClass.setAcademicYearId(request.getAcademicYearId());
        liveClass.setClassId(request.getClassId());
        liveClass.setSectionId(request.getSectionId());
        liveClass.setSubjectId(request.getSubjectId());
        liveClass.setTeacherId(request.getTeacherId());
        liveClass.setScheduledStartTime(request.getScheduledStartTime());
        liveClass.setScheduledEndTime(request.getScheduledEndTime());
        liveClass.setProviderName(request.getProviderName());
        liveClass.setProviderMeetingId(providerResponse.getProviderMeetingId());
        liveClass.setJoinUrl(providerResponse.getJoinUrl());
        liveClass.setHostUrl(providerResponse.getHostUrl());
        liveClass.setPassword(providerResponse.getPassword());
        liveClass.setRecordingEnabled(request.isRecordingEnabled());

        LiveClass savedLiveClass = liveClassRepository.save(liveClass);
        createParticipants(savedLiveClass.getId(), request.getStudentIds());

        return toResponse(savedLiveClass);
    }

    public List<LiveClassResponse> getLiveClasses(Long classId, Long sectionId, Long teacherId) {
        if (classId != null && sectionId != null) {
            return liveClassRepository.findByClassIdAndSectionId(classId, sectionId)
                    .stream().map(this::toResponse).toList();
        }

        if (teacherId != null) {
            return liveClassRepository.findByTeacherId(teacherId)
                    .stream().map(this::toResponse).toList();
        }

        return liveClassRepository.findAll().stream().map(this::toResponse).toList();
    }

    public LiveClassResponse getLiveClassById(Long id) {
        return toResponse(findLiveClass(id));
    }

    public List<LiveClassResponse> getStudentLiveClasses(Long studentId) {
        return participantRepository.findByStudentId(studentId)
                .stream()
                .map(participant -> findLiveClass(participant.getLiveClassId()))
                .map(this::toResponse)
                .toList();
    }

    public LiveClassResponse startLiveClass(Long id) {
        LiveClass liveClass = findLiveClass(id);

        if (liveClass.getStatus() == LiveClassStatus.CANCELLED) {
            throw new RuntimeException("Cancelled live class cannot be started");
        }

        liveClass.setStatus(LiveClassStatus.STARTED);
        liveClass.setActualStartTime(LocalDateTime.now());

        LiveClass savedLiveClass = liveClassRepository.save(liveClass);

        // AuditLog Live class started
        auditLogService.log(
                AuditAction.LIVE_CLASS_STARTED,
                "LIVE_CLASS",
                "LiveClass",
                savedLiveClass.getId(),
                "Live class started: " + savedLiveClass.getTitle(),
                null,
                SecurityUtils.getCurrentUsername(),
                null,
                null
        );

        return toResponse(savedLiveClass);
    }

    public LiveClassResponse completeLiveClass(Long id) {
        LiveClass liveClass = findLiveClass(id);
        liveClass.setStatus(LiveClassStatus.COMPLETED);
        liveClass.setActualEndTime(LocalDateTime.now());

        markLiveClassAttendance(id);

        LiveClass savedLiveClass = liveClassRepository.save(liveClass);

        auditLogService.log(
                AuditAction.LIVE_CLASS_COMPLETED,
                "LIVE_CLASS",
                "LiveClass",
                savedLiveClass.getId(),
                "Live class completed: " + savedLiveClass.getTitle(),
                null,
                SecurityUtils.getCurrentUsername(),
                null,
                null
        );

        return toResponse(savedLiveClass);
    }

    public LiveClassResponse cancelLiveClass(Long id) {
        LiveClass liveClass = findLiveClass(id);
        LiveClassProviderService provider = providerFactory.getProvider(liveClass.getProviderName());

        provider.cancelMeeting(liveClass.getProviderMeetingId());

        liveClass.setStatus(LiveClassStatus.CANCELLED);
        return toResponse(liveClassRepository.save(liveClass));
    }

    public LiveClassResponse rescheduleLiveClass(Long id, RescheduleLiveClassRequest request) {
        LiveClass liveClass = findLiveClass(id);
        LiveClassProviderService provider = providerFactory.getProvider(liveClass.getProviderName());

        provider.updateMeeting(liveClass.getProviderMeetingId(),
                new LiveClassProviderService.ProviderMeetingRequest(
                        liveClass.getTitle(),
                        request.getScheduledStartTime(),
                        request.getScheduledEndTime(),
                        liveClass.isRecordingEnabled()
                ));

        liveClass.setScheduledStartTime(request.getScheduledStartTime());
        liveClass.setScheduledEndTime(request.getScheduledEndTime());
        liveClass.setStatus(LiveClassStatus.RESCHEDULED);

        return toResponse(liveClassRepository.save(liveClass));
    }

    public JoinLiveClassResponse joinLiveClass(Long id, JoinLiveClassRequest request) {
        LiveClass liveClass = findLiveClass(id);

        if (liveClass.getStatus() == LiveClassStatus.CANCELLED) {
            throw new RuntimeException("Cannot join cancelled live class");
        }

        LiveClassParticipant participant = participantRepository
                .findByLiveClassIdAndStudentId(id, request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student is not invited to this live class"));

        participant.setJoinedAt(LocalDateTime.now());
        participant.setParticipationStatus(ParticipantStatus.JOINED);
        participantRepository.save(participant);

        return new JoinLiveClassResponse(
                liveClass.getId(),
                request.getStudentId(),
                liveClass.getJoinUrl(),
                liveClass.getPassword()
        );
    }

    public ParticipantResponse leaveLiveClass(Long id, LeaveLiveClassRequest request) {
        LiveClassParticipant participant = participantRepository
                .findByLiveClassIdAndStudentId(id, request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Participant not found"));

        participant.setLeftAt(LocalDateTime.now());
        participant.calculateDuration();
        participant.setParticipationStatus(ParticipantStatus.LEFT);

        return toParticipantResponse(participantRepository.save(participant));
    }

    public String handleWebhook(String providerName, LiveClassWebhookRequest request) {
        LiveClassProviderService provider = providerFactory.getProvider(providerName);

        if (!provider.verifyWebhook(request.getPayload(), null)) {
            throw new RuntimeException("Invalid live class webhook");
        }

        LiveClassProviderEvent event = new LiveClassProviderEvent();
        event.setLiveClassId(request.getLiveClassId());
        event.setProviderName(providerName);
        event.setEventType(request.getEventType());
        event.setPayload(request.getPayload());
        event.setProcessed(true);

        providerEventRepository.save(event);

        return "Live class webhook recorded successfully";
    }

    private void createParticipants(Long liveClassId, List<Long> studentIds) {
        if (studentIds == null || studentIds.isEmpty()) {
            return;
        }

        List<LiveClassParticipant> participants = studentIds.stream()
                .filter(studentId -> !participantRepository.existsByLiveClassIdAndStudentId(liveClassId, studentId))
                .map(studentId -> {
                    LiveClassParticipant participant = new LiveClassParticipant();
                    participant.setLiveClassId(liveClassId);
                    participant.setStudentId(studentId);
                    participant.setParticipationStatus(ParticipantStatus.INVITED);
                    return participant;
                })
                .toList();

        participantRepository.saveAll(participants);
    }

    private void markLiveClassAttendance(Long liveClassId) {
        List<LiveClassParticipant> participants = participantRepository.findByLiveClassId(liveClassId);

        List<LiveClassAttendance> attendanceList = participants.stream().map(participant -> {
            LiveClassAttendance attendance = new LiveClassAttendance();
            attendance.setLiveClassId(liveClassId);
            attendance.setStudentId(participant.getStudentId());
            attendance.setDurationInMinutes(participant.getDurationInMinutes());
            attendance.setAttendanceStatus(participant.getParticipationStatus() == ParticipantStatus.LEFT
                    || participant.getParticipationStatus() == ParticipantStatus.JOINED
                    ? AttendanceStatus.PRESENT
                    : AttendanceStatus.ABSENT);
            return attendance;
        }).toList();

        attendanceRepository.saveAll(attendanceList);
    }

    private LiveClass findLiveClass(Long id) {
        return liveClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Live class not found with id: " + id));
    }

    private LiveClassResponse toResponse(LiveClass liveClass) {
        List<ParticipantResponse> participants = participantRepository.findByLiveClassId(liveClass.getId())
                .stream()
                .map(this::toParticipantResponse)
                .toList();

        return new LiveClassResponse(
                liveClass.getId(),
                liveClass.getTitle(),
                liveClass.getClassId(),
                liveClass.getSectionId(),
                liveClass.getSubjectId(),
                liveClass.getTeacherId(),
                liveClass.getScheduledStartTime(),
                liveClass.getScheduledEndTime(),
                liveClass.getProviderName(),
                liveClass.getProviderMeetingId(),
                liveClass.getJoinUrl(),
                liveClass.getHostUrl(),
                liveClass.getStatus(),
                liveClass.isRecordingEnabled(),
                participants
        );
    }

    private ParticipantResponse toParticipantResponse(LiveClassParticipant participant) {
        return new ParticipantResponse(
                participant.getId(),
                participant.getStudentId(),
                participant.getJoinedAt(),
                participant.getLeftAt(),
                participant.getDurationInMinutes(),
                participant.getParticipationStatus()
        );
    }
}