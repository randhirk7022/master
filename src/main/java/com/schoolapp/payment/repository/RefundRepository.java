package com.schoolapp.payment.repository;

import com.schoolapp.payment.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {
    List<Refund> findByPaymentTransactionId(Long paymentTransactionId);
}