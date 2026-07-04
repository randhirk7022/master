package com.schoolapp.fee.dto;

import com.schoolapp.fee.entity.FeePaymentMode;
import com.schoolapp.fee.entity.InvoiceStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FeeResponses {

    public static class InvoiceResponse {
        private Long id;
        private String invoiceNumber;
        private Long studentId;
        private BigDecimal totalAmount;
        private BigDecimal paidAmount;
        private BigDecimal dueAmount;
        private LocalDate dueDate;
        private InvoiceStatus status;
        private List<InvoiceItemResponse> items;

        public InvoiceResponse(Long id, String invoiceNumber, Long studentId, BigDecimal totalAmount,
                               BigDecimal paidAmount, BigDecimal dueAmount, LocalDate dueDate,
                               InvoiceStatus status, List<InvoiceItemResponse> items) {
            this.id = id;
            this.invoiceNumber = invoiceNumber;
            this.studentId = studentId;
            this.totalAmount = totalAmount;
            this.paidAmount = paidAmount;
            this.dueAmount = dueAmount;
            this.dueDate = dueDate;
            this.status = status;
            this.items = items;
        }

        public Long getId() { return id; }
        public String getInvoiceNumber() { return invoiceNumber; }
        public Long getStudentId() { return studentId; }
        public BigDecimal getTotalAmount() { return totalAmount; }
        public BigDecimal getPaidAmount() { return paidAmount; }
        public BigDecimal getDueAmount() { return dueAmount; }
        public LocalDate getDueDate() { return dueDate; }
        public InvoiceStatus getStatus() { return status; }
        public List<InvoiceItemResponse> getItems() { return items; }
    }

    public static class InvoiceItemResponse {
        private Long id;
        private Long feeCategoryId;
        private BigDecimal amount;

        public InvoiceItemResponse(Long id, Long feeCategoryId, BigDecimal amount) {
            this.id = id;
            this.feeCategoryId = feeCategoryId;
            this.amount = amount;
        }

        public Long getId() { return id; }
        public Long getFeeCategoryId() { return feeCategoryId; }
        public BigDecimal getAmount() { return amount; }
    }

    public static class FeePaymentResponse {
        private Long id;
        private Long invoiceId;
        private Long studentId;
        private BigDecimal amount;
        private FeePaymentMode paymentMode;
        private String referenceNumber;
        private LocalDateTime paidAt;

        public FeePaymentResponse(Long id, Long invoiceId, Long studentId, BigDecimal amount,
                                  FeePaymentMode paymentMode, String referenceNumber, LocalDateTime paidAt) {
            this.id = id;
            this.invoiceId = invoiceId;
            this.studentId = studentId;
            this.amount = amount;
            this.paymentMode = paymentMode;
            this.referenceNumber = referenceNumber;
            this.paidAt = paidAt;
        }

        public Long getId() { return id; }
        public Long getInvoiceId() { return invoiceId; }
        public Long getStudentId() { return studentId; }
        public BigDecimal getAmount() { return amount; }
        public FeePaymentMode getPaymentMode() { return paymentMode; }
        public String getReferenceNumber() { return referenceNumber; }
        public LocalDateTime getPaidAt() { return paidAt; }
    }

    public static class FeeReportResponse {
        private BigDecimal totalAmount;
        private BigDecimal paidAmount;
        private BigDecimal dueAmount;

        public FeeReportResponse(BigDecimal totalAmount, BigDecimal paidAmount, BigDecimal dueAmount) {
            this.totalAmount = totalAmount;
            this.paidAmount = paidAmount;
            this.dueAmount = dueAmount;
        }

        public BigDecimal getTotalAmount() { return totalAmount; }
        public BigDecimal getPaidAmount() { return paidAmount; }
        public BigDecimal getDueAmount() { return dueAmount; }
    }
}