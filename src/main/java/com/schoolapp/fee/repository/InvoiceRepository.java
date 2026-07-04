package com.schoolapp.fee.repository;

import com.schoolapp.fee.entity.Invoice;
import com.schoolapp.fee.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);
    Optional<Invoice> findById(Long id);
    List<Invoice> findByStudentId(Long studentId);
    List<Invoice> findByStatus(InvoiceStatus status);
}