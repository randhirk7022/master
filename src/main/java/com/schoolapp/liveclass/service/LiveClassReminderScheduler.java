package com.schoolapp.liveclass.service;

import com.schoolapp.liveclass.entity.LiveClassStatus;
import com.schoolapp.liveclass.repository.LiveClassParticipantRepository;
import com.schoolapp.liveclass.repository.LiveClassRepository;
import com.schoolapp.notification.service.NotificationTriggerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LiveClassReminderScheduler {

    private final LiveClassRepository liveClassRepository;
    private final LiveClassParticipantRepository participantRepository;
    private final NotificationTriggerService notificationTriggerService;

    public LiveClassReminderScheduler(LiveClassRepository liveClassRepository,
                                      LiveClassParticipantRepository participantRepository,
                                      NotificationTriggerService notificationTriggerService) {
        this.liveClassRepository = liveClassRepository;
        this.participantRepository = participantRepository;
        this.notificationTriggerService = notificationTriggerService;
    }

    @Scheduled(fixedRate = 60000)
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderWindow = now.plusMinutes(15);

        liveClassRepository.findByStatus(LiveClassStatus.SCHEDULED).stream()
                .filter(liveClass -> !liveClass.isReminderSent())
                .filter(liveClass -> liveClass.getScheduledStartTime() != null)
                .filter(liveClass -> !liveClass.getScheduledStartTime().isBefore(now))
                .filter(liveClass -> !liveClass.getScheduledStartTime().isAfter(reminderWindow))
                .forEach(liveClass -> {
                    var studentIds = participantRepository.findByLiveClassId(liveClass.getId())
                            .stream()
                            .map(participant -> participant.getStudentId())
                            .toList();

                    notificationTriggerService.sendLiveClassReminder(
                            studentIds,
                            liveClass.getTitle(),
                            liveClass.getScheduledStartTime()
                    );

                    liveClass.setReminderSent(true);
                    liveClassRepository.save(liveClass);
                });
    }
}