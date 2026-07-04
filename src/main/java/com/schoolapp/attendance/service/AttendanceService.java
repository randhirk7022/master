package com.schoolapp.attendance.service;

import com.schoolapp.attendance.dto.*;
import com.schoolapp.attendance.entity.*;
import com.schoolapp.attendance.repository.AttendanceRepository;
import com.schoolapp.notification.service.NotificationTriggerService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final NotificationTriggerService notificationTriggerService;

    public AttendanceService(AttendanceRepository attendanceRepository,
    		NotificationTriggerService notificationTriggerService) {
        this.attendanceRepository = attendanceRepository;
        this.notificationTriggerService = notificationTriggerService;
    }

    public List<AttendanceResponse> markAttendance(MarkAttendanceRequest request) {
        List<Attendance> attendanceList = request.getRecords()
                .stream()
                .map(record -> {
                    Attendance attendance = attendanceRepository
                            .findByStudentIdAndAttendanceDate(
                                    record.getStudentId(),
                                    request.getAttendanceDate()
                            )
                            .orElseGet(Attendance::new);

                    attendance.setStudentId(record.getStudentId());
                    attendance.setClassId(request.getClassId());
                    attendance.setSectionId(request.getSectionId());
                    attendance.setAttendanceDate(request.getAttendanceDate());
                    attendance.setMarkedByTeacherId(request.getMarkedByTeacherId());
                    attendance.setStatus(record.getStatus());
                    attendance.setRemarks(record.getRemarks());

                    return attendance;
                })
                .toList();

        List<Attendance> savedRecords = attendanceRepository.saveAll(attendanceList);

        savedRecords.stream()
                .filter(record -> record.getStatus() == AttendanceStatus.ABSENT)
                .forEach(record -> notificationTriggerService.sendAbsenceAlert(
                        record.getStudentId(),
                        record.getAttendanceDate()
                ));

        return savedRecords.stream()
                .map(this::toResponse)
                .toList();
    }
    
    public List<AttendanceResponse> getAttendance(
            Long classId,
            Long sectionId,
            LocalDate attendanceDate
    ) {
        return attendanceRepository
                .findByClassIdAndSectionIdAndAttendanceDate(classId, sectionId, attendanceDate)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AttendanceSummaryResponse getStudentSummary(
            Long studentId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        List<Attendance> records = attendanceRepository
                .findByStudentIdAndAttendanceDateBetween(studentId, startDate, endDate);

        long totalDays = records.size();

        long presentDays = records.stream()
                .filter(record -> record.getStatus() == AttendanceStatus.PRESENT)
                .count();

        long absentDays = records.stream()
                .filter(record -> record.getStatus() == AttendanceStatus.ABSENT)
                .count();

        long lateDays = records.stream()
                .filter(record -> record.getStatus() == AttendanceStatus.LATE)
                .count();

        long leaveDays = records.stream()
                .filter(record -> record.getStatus() == AttendanceStatus.LEAVE)
                .count();

        return new AttendanceSummaryResponse(
                studentId,
                totalDays,
                presentDays,
                absentDays,
                lateDays,
                leaveDays
        );
    }

    private AttendanceResponse toResponse(Attendance attendance) {
        return new AttendanceResponse(
                attendance.getId(),
                attendance.getStudentId(),
                attendance.getClassId(),
                attendance.getSectionId(),
                attendance.getAttendanceDate(),
                attendance.getStatus(),
                attendance.getMarkedByTeacherId(),
                attendance.getRemarks()
        );
    }
}