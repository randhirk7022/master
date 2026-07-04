package com.schoolapp.payment.dto;

import com.schoolapp.payment.entity.PaymentMethod;
import com.schoolapp.payment.entity.PaymentStatus;
import com.schoolapp.payment.entity.RefundStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponses {

    public static class PaymentOrderResponse {
        private Long id;
        private String orderNumber;
        private Long invoiceId;
        private Long studentId;
        private BigDecimal amount;
        private String currency;
        private String gatewayName;
        private String gatewayOrderId;
        private PaymentStatus status;
        private String checkoutUrl;
        private PaymentMethod preferredPaymentMethod;

        public PaymentOrderResponse(
                Long id,
                String orderNumber,
                Long invoiceId,
                Long studentId,
                BigDecimal amount,
                String currency,
                String gatewayName,
                String gatewayOrderId,
                PaymentStatus status,
                String checkoutUrl,
                PaymentMethod preferredPaymentMethod
        ) {
            this.id = id;
            this.orderNumber = orderNumber;
            this.invoiceId = invoiceId;
            this.studentId = studentId;
            this.amount = amount;
            this.currency = currency;
            this.gatewayName = gatewayName;
            this.gatewayOrderId = gatewayOrderId;
            this.status = status;
            this.checkoutUrl = checkoutUrl;
            this.preferredPaymentMethod = preferredPaymentMethod;
        }

        public Long getId() { return id; }
        public String getOrderNumber() { return orderNumber; }
        public Long getInvoiceId() { return invoiceId; }
        public Long getStudentId() { return studentId; }
        public BigDecimal getAmount() { return amount; }
        public String getCurrency() { return currency; }
        public String getGatewayName() { return gatewayName; }
        public String getGatewayOrderId() { return gatewayOrderId; }
        public PaymentStatus getStatus() { return status; }
        public String getCheckoutUrl() {
            return checkoutUrl;
        }
        public PaymentMethod getPreferredPaymentMethod() {
            return preferredPaymentMethod;
        }
    }

    public static class PaymentTransactionResponse {
        private Long id;
        private Long paymentOrderId;
        private String gatewayPaymentId;
        private BigDecimal amount;
        private PaymentStatus status;
        private String paymentMode;
        private LocalDateTime paidAt;

        public PaymentTransactionResponse(Long id, Long paymentOrderId, String gatewayPaymentId,
                                          BigDecimal amount, PaymentStatus status,
                                          String paymentMode, LocalDateTime paidAt) {
            this.id = id;
            this.paymentOrderId = paymentOrderId;
            this.gatewayPaymentId = gatewayPaymentId;
            this.amount = amount;
            this.status = status;
            this.paymentMode = paymentMode;
            this.paidAt = paidAt;
        }

        public Long getId() { return id; }
        public Long getPaymentOrderId() { return paymentOrderId; }
        public String getGatewayPaymentId() { return gatewayPaymentId; }
        public BigDecimal getAmount() { return amount; }
        public PaymentStatus getStatus() { return status; }
        public String getPaymentMode() { return paymentMode; }
        public LocalDateTime getPaidAt() { return paidAt; }
    }

    public static class ReceiptResponse {
        private Long id;
        private String receiptNumber;
        private Long invoiceId;
        private Long paymentTransactionId;
        private BigDecimal amount;
        private LocalDateTime generatedAt;

        public ReceiptResponse(Long id, String receiptNumber, Long invoiceId,
                               Long paymentTransactionId, BigDecimal amount, LocalDateTime generatedAt) {
            this.id = id;
            this.receiptNumber = receiptNumber;
            this.invoiceId = invoiceId;
            this.paymentTransactionId = paymentTransactionId;
            this.amount = amount;
            this.generatedAt = generatedAt;
        }

        public Long getId() { return id; }
        public String getReceiptNumber() { return receiptNumber; }
        public Long getInvoiceId() { return invoiceId; }
        public Long getPaymentTransactionId() { return paymentTransactionId; }
        public BigDecimal getAmount() { return amount; }
        public LocalDateTime getGeneratedAt() { return generatedAt; }
    }

    public static class RefundResponse {
        private Long id;
        private Long paymentTransactionId;
        private BigDecimal refundAmount;
        private RefundStatus status;
        private String reason;

        public RefundResponse(Long id, Long paymentTransactionId, BigDecimal refundAmount,
                              RefundStatus status, String reason) {
            this.id = id;
            this.paymentTransactionId = paymentTransactionId;
            this.refundAmount = refundAmount;
            this.status = status;
            this.reason = reason;
        }

        public Long getId() { return id; }
        public Long getPaymentTransactionId() { return paymentTransactionId; }
        public BigDecimal getRefundAmount() { return refundAmount; }
        public RefundStatus getStatus() { return status; }
        public String getReason() { return reason; }
    }
}