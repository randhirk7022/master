package com.schoolapp.notification.service;

import com.schoolapp.auth.repository.UserRepository;
import com.schoolapp.notification.dto.NotificationRequests.*;
import com.schoolapp.notification.entity.NotificationTemplate;
import com.schoolapp.notification.repository.NotificationTemplateRepository;
import com.schoolapp.parent.entity.Parent;
import com.schoolapp.parent.entity.StudentParent;
import com.schoolapp.parent.repository.ParentRepository;
import com.schoolapp.parent.repository.StudentParentRepository;
import com.schoolapp.student.entity.Student;
import com.schoolapp.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class NotificationTriggerService {

    private final NotificationTemplateRepository templateRepository;
    private final NotificationService notificationService;
    private final StudentParentRepository studentParentRepository;
    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public NotificationTriggerService(NotificationTemplateRepository templateRepository,
                                      NotificationService notificationService,
                                      StudentParentRepository studentParentRepository,
                                      ParentRepository parentRepository,
                                      StudentRepository studentRepository,
                                      UserRepository userRepository) {
        this.templateRepository = templateRepository;
        this.notificationService = notificationService;
        this.studentParentRepository = studentParentRepository;
        this.parentRepository = parentRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public void sendPaymentSuccess(Long studentId, BigDecimal amount, String receiptNumber) {
        sendToParents(studentId, "PAYMENT_SUCCESS_WHATSAPP", "PAYMENT", Map.of(
                "studentName", getStudentName(studentId),
                "amount", amount.toString(),
                "receiptNumber", receiptNumber
        ));
    }

    public void sendAbsenceAlert(Long studentId, LocalDate attendanceDate) {
        sendToParents(studentId, "ABSENCE_ALERT_SMS", "ATTENDANCE", Map.of(
                "studentName", getStudentName(studentId),
                "date", attendanceDate.toString()
        ));
    }

    public void sendNoticePublished(List<Long> recipientUserIds, String title) {
        sendToUsers(recipientUserIds, "NOTICE_PUBLISHED_WHATSAPP", "NOTICE", Map.of(
                "noticeTitle", title
        ));
    }

    public void sendLiveClassReminder(List<Long> studentIds, String title, LocalDateTime startTime) {
        studentIds.forEach(studentId -> sendToParents(studentId, "LIVE_CLASS_REMINDER_WHATSAPP", "LIVE_CLASS", Map.of(
                "studentName", getStudentName(studentId),
                "classTitle", title,
                "startTime", startTime.toString()
        )));
    }

    public void sendRecordingAvailable(Long studentId, String title) {
        sendToParents(studentId, "RECORDING_AVAILABLE_WHATSAPP", "RECORDED_CLASS", Map.of(
                "studentName", getStudentName(studentId),
                "recordingTitle", title
        ));
    }

    private void sendToParents(Long studentId, String templateName, String module, Map<String, String> variables) {
        templateRepository.findByTemplateName(templateName)
                .filter(NotificationTemplate::isActive)
                .ifPresent(template -> {
                    List<RecipientRequest> recipients = studentParentRepository.findByStudentId(studentId)
                            .stream()
                            .map(StudentParent::getParentId)
                            .map(parentRepository::findById)
                            .flatMap(parent -> parent.stream())
                            .map(this::toRecipient)
                            .toList();

                    if (!recipients.isEmpty()) {
                        notificationService.sendNotification(new SendNotificationRequest(
                                template.getId(), module, recipients, variables
                        ));
                    }
                });
    }

    private void sendToUsers(List<Long> userIds, String templateName, String module, Map<String, String> variables) {
        if (userIds == null || userIds.isEmpty()) {
            return;
        }

        templateRepository.findByTemplateName(templateName)
                .filter(NotificationTemplate::isActive)
                .ifPresent(template -> {
                    List<RecipientRequest> recipients = userIds.stream()
                            .map(userRepository::findById)
                            .flatMap(user -> user.stream())
                            .map(user -> new RecipientRequest(user.getId(), user.getPhoneNumber(), user.getPhoneNumber()))
                            .toList();

                    if (!recipients.isEmpty()) {
                        notificationService.sendNotification(new SendNotificationRequest(
                                template.getId(), module, recipients, variables
                        ));
                    }
                });
    }

    private RecipientRequest toRecipient(Parent parent) {
        return new RecipientRequest(parent.getUserId(), parent.getPhoneNumber(), parent.getWhatsappNumber());
    }

    private String getStudentName(Long studentId) {
        return studentRepository.findById(studentId)
                .map(Student::getFirstName)
                .orElse("Student");
    }
}