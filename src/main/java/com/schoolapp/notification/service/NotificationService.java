package com.schoolapp.notification.service;

import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.notification.dto.NotificationRequests.*;
import com.schoolapp.notification.dto.NotificationResponses.*;
import com.schoolapp.notification.entity.*;
import com.schoolapp.notification.provider.NotificationProvider;
import com.schoolapp.notification.provider.NotificationProviderFactory;
import com.schoolapp.notification.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final NotificationTemplateRepository templateRepository;
    private final NotificationRequestRepository requestRepository;
    private final NotificationRecipientRepository recipientRepository;
    private final NotificationDeliveryLogRepository deliveryLogRepository;
    private final NotificationWebhookEventRepository webhookEventRepository;
    private final NotificationProviderFactory providerFactory;

    public NotificationService(NotificationTemplateRepository templateRepository,
                               NotificationRequestRepository requestRepository,
                               NotificationRecipientRepository recipientRepository,
                               NotificationDeliveryLogRepository deliveryLogRepository,
                               NotificationWebhookEventRepository webhookEventRepository,
                               NotificationProviderFactory providerFactory) {
        this.templateRepository = templateRepository;
        this.requestRepository = requestRepository;
        this.recipientRepository = recipientRepository;
        this.deliveryLogRepository = deliveryLogRepository;
        this.webhookEventRepository = webhookEventRepository;
        this.providerFactory = providerFactory;
    }

    public NotificationTemplate createTemplate(CreateTemplateRequest request) {
        if (templateRepository.existsByTemplateName(request.getTemplateName())) {
            throw new RuntimeException("Notification template already exists");
        }

        NotificationTemplate template = new NotificationTemplate();
        template.setTemplateName(request.getTemplateName());
        template.setChannel(request.getChannel());
        template.setSubject(request.getSubject());
        template.setBody(request.getBody());
        template.setProviderTemplateId(request.getProviderTemplateId());
        template.setActive(request.isActive());

        return templateRepository.save(template);
    }

    public List<NotificationTemplate> getTemplates() {
        return templateRepository.findAll();
    }

    public NotificationTemplate updateTemplate(Long id, CreateTemplateRequest request) {
        NotificationTemplate template = findTemplate(id);
        template.setTemplateName(request.getTemplateName());
        template.setChannel(request.getChannel());
        template.setSubject(request.getSubject());
        template.setBody(request.getBody());
        template.setProviderTemplateId(request.getProviderTemplateId());
        template.setActive(request.isActive());
        return templateRepository.save(template);
    }

    @Transactional
    public NotificationRequestResponse sendNotification(SendNotificationRequest request) {
        NotificationTemplate template = findTemplate(request.getTemplateId());

        if (!template.isActive()) {
            throw new RuntimeException("Notification template is inactive");
        }

        NotificationProvider provider = providerFactory.getProvider(template.getChannel());

        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTemplateId(template.getId());
        notificationRequest.setChannel(template.getChannel());
        notificationRequest.setTriggeredByModule(request.getTriggeredByModule());
        notificationRequest.setStatus(NotificationStatus.QUEUED);

        NotificationRequest savedRequest = requestRepository.save(notificationRequest);

        List<NotificationRecipient> recipients = request.getRecipients().stream().map(recipientRequest -> {
            String message = applyVariables(template.getBody(), request.getVariables());

            NotificationRecipient recipient = new NotificationRecipient();
            recipient.setNotificationRequestId(savedRequest.getId());
            recipient.setRecipientUserId(recipientRequest.getRecipientUserId());
            recipient.setPhoneNumber(recipientRequest.getPhoneNumber());
            recipient.setWhatsappNumber(recipientRequest.getWhatsappNumber());
            recipient.setMessageBody(message);

            String to = template.getChannel() == NotificationChannel.WHATSAPP
                    ? recipientRequest.getWhatsappNumber()
                    : recipientRequest.getPhoneNumber();

            NotificationProvider.NotificationSendResponse providerResponse =
                    provider.send(new NotificationProvider.NotificationSendRequest(to, message));

            recipient.setStatus(providerResponse.isSuccess()
                    ? NotificationStatus.SENT
                    : NotificationStatus.FAILED);

            NotificationRecipient savedRecipient = recipientRepository.save(recipient);

            NotificationDeliveryLog log = new NotificationDeliveryLog();
            log.setRecipientId(savedRecipient.getId());
            log.setProviderName(provider.getProviderName());
            log.setProviderMessageId(providerResponse.getProviderMessageId());
            log.setProviderResponse(providerResponse.getRawResponse());
            log.setStatus(savedRecipient.getStatus());

            deliveryLogRepository.save(log);

            return savedRecipient;
        }).toList();

        boolean allSent = recipients.stream().allMatch(recipient -> recipient.getStatus() == NotificationStatus.SENT);
        savedRequest.setStatus(allSent ? NotificationStatus.SENT : NotificationStatus.FAILED);
        requestRepository.save(savedRequest);

        return toRequestResponse(savedRequest);
    }

    public List<NotificationRequestResponse> getRequests() {
        return requestRepository.findAll()
                .stream()
                .map(this::toRequestResponse)
                .toList();
    }

    public NotificationRequestResponse getRequestById(Long id) {
        NotificationRequest request = requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification request not found"));
        return toRequestResponse(request);
    }

    @Transactional
    public String handleWebhook(String providerName, NotificationWebhookRequest request) {
        if (webhookEventRepository.existsByEventId(request.getEventId())) {
            return "Duplicate webhook ignored";
        }

        NotificationWebhookEvent event = new NotificationWebhookEvent();
        event.setProviderName(providerName);
        event.setEventId(request.getEventId());
        event.setEventType(request.getEventType());
        event.setPayload(request.getPayload());
        event.setProcessed(true);

        webhookEventRepository.save(event);

        return "Notification webhook recorded successfully";
    }

    private NotificationTemplate findTemplate(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification template not found with id: " + id));
    }

    private String applyVariables(String body, Map<String, String> variables) {
        if (variables == null || variables.isEmpty()) {
            return body;
        }

        String message = body;

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            message = message.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return message;
    }

    private NotificationRequestResponse toRequestResponse(NotificationRequest request) {
        List<NotificationRecipientResponse> recipients = recipientRepository
                .findByNotificationRequestId(request.getId())
                .stream()
                .map(recipient -> new NotificationRecipientResponse(
                        recipient.getId(),
                        recipient.getRecipientUserId(),
                        recipient.getPhoneNumber(),
                        recipient.getWhatsappNumber(),
                        recipient.getStatus()
                ))
                .toList();

        return new NotificationRequestResponse(
                request.getId(),
                request.getTemplateId(),
                request.getChannel(),
                request.getTriggeredByModule(),
                request.getStatus(),
                recipients
        );
    }
}