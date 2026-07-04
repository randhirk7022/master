package com.schoolapp.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import com.schoolapp.payment.entity.PaymentMethod;

public class PaymentRequests {

	public static class CreatePaymentOrderRequest {
	    @NotNull
	    private Long invoiceId;

	    @NotBlank
	    private String gatewayName;

	    private PaymentMethod preferredPaymentMethod;

	    public Long getInvoiceId() {
	        return invoiceId;
	    }

	    public String getGatewayName() {
	        return gatewayName;
	    }

	    public PaymentMethod getPreferredPaymentMethod() {
	        return preferredPaymentMethod;
	    }
	}

    public static class ConfirmPaymentRequest {
        @NotNull
        private Long paymentOrderId;
        @NotBlank
        private String gatewayPaymentId;
        private String gatewaySignature;
        @NotNull
        private BigDecimal amount;
        private String paymentMode;
        private String rawResponse;

        public Long getPaymentOrderId() { return paymentOrderId; }
        public String getGatewayPaymentId() { return gatewayPaymentId; }
        public String getGatewaySignature() { return gatewaySignature; }
        public BigDecimal getAmount() { return amount; }
        public String getPaymentMode() { return paymentMode; }
        public String getRawResponse() { return rawResponse; }
    }

    public static class WebhookRequest {
        @NotBlank
        private String eventId;
        @NotBlank
        private String eventType;
        @NotBlank
        private String payload;

        public String getEventId() { return eventId; }
        public String getEventType() { return eventType; }
        public String getPayload() { return payload; }
    }

    public static class RefundRequest {
        @NotNull
        private Long paymentTransactionId;
        @NotNull
        private BigDecimal refundAmount;
        private String reason;

        public Long getPaymentTransactionId() { return paymentTransactionId; }
        public BigDecimal getRefundAmount() { return refundAmount; }
        public String getReason() { return reason; }
    }
}