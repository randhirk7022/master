package com.schoolapp.fee.repository;

import com.schoolapp.fee.entity.FeePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeePaymentRepository extends JpaRepository<FeePayment, Long> {
    List<FeePayment> findByInvoiceId(Long invoiceId);
    List<FeePayment> findByStudentId(Long studentId);
}