package com.schoolapp.payment.repository;

import com.schoolapp.payment.entity.PaymentWebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentWebhookEventRepository extends JpaRepository<PaymentWebhookEvent, Long> {
    boolean existsByEventId(String eventId);
}