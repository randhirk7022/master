package com.schoolapp.payment.repository;

import com.schoolapp.payment.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Optional<Receipt> findByPaymentTransactionId(Long paymentTransactionId);
}