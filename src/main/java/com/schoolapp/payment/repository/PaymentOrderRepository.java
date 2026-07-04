package com.schoolapp.payment.repository;

import com.schoolapp.payment.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    Optional<PaymentOrder> findByOrderNumber(String orderNumber);
    Optional<PaymentOrder> findByGatewayOrderId(String gatewayOrderId);
}