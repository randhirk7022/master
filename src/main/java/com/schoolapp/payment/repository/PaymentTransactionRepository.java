package com.schoolapp.payment.repository;

import com.schoolapp.payment.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    Optional<PaymentTransaction> findByGatewayPaymentId(String gatewayPaymentId);
    List<PaymentTransaction> findByPaymentOrderId(Long paymentOrderId);
}