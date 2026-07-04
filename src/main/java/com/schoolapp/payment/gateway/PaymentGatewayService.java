package com.schoolapp.payment.gateway;

import java.math.BigDecimal;

import com.schoolapp.payment.entity.PaymentMethod;

public interface PaymentGatewayService {

	GatewayOrderResponse createOrder(GatewayOrderRequest request);

	boolean verifyWebhook(String payload, String signature);

	boolean verifyPaymentSignature(String orderId, String paymentId, String signature);

	GatewayRefundResponse initiateRefund(GatewayRefundRequest request);

	String getGatewayName();

	class GatewayOrderRequest {
		private String orderNumber;
		private BigDecimal amount;
		private String currency;
		private String customerName;
		private String customerEmail;
		private String customerPhone;
		private PaymentMethod preferredPaymentMethod;

		public GatewayOrderRequest(String orderNumber, BigDecimal amount, String currency, String customerName,
				String customerEmail, String customerPhone, PaymentMethod preferredPaymentMethod) {
			this.orderNumber = orderNumber;
			this.amount = amount;
			this.currency = currency;
			this.customerName = customerName;
			this.customerEmail = customerEmail;
			this.customerPhone = customerPhone;
			this.preferredPaymentMethod = preferredPaymentMethod;
		}

		public String getOrderNumber() {
			return orderNumber;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public String getCurrency() {
			return currency;
		}

		public String getCustomerName() {
			return customerName;
		}

		public String getCustomerEmail() {
			return customerEmail;
		}

		public String getCustomerPhone() {
			return customerPhone;
		}

		public PaymentMethod getPreferredPaymentMethod() {
			return preferredPaymentMethod;
		}
	}

	class GatewayOrderResponse {
		private String gatewayOrderId;
		private String checkoutUrl;
		private String rawResponse;

		public GatewayOrderResponse(String gatewayOrderId, String checkoutUrl, String rawResponse) {
			this.gatewayOrderId = gatewayOrderId;
			this.checkoutUrl = checkoutUrl;
			this.rawResponse = rawResponse;
		}

		public String getGatewayOrderId() {
			return gatewayOrderId;
		}

		public String getCheckoutUrl() {
			return checkoutUrl;
		}

		public String getRawResponse() {
			return rawResponse;
		}
	}

	class GatewayRefundRequest {
		private String gatewayPaymentId;
		private BigDecimal amount;
		private String reason;

		public GatewayRefundRequest(String gatewayPaymentId, BigDecimal amount, String reason) {
			this.gatewayPaymentId = gatewayPaymentId;
			this.amount = amount;
			this.reason = reason;
		}

		public String getGatewayPaymentId() {
			return gatewayPaymentId;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public String getReason() {
			return reason;
		}
	}

	class GatewayRefundResponse {
		private String gatewayRefundId;
		private String status;
		private String rawResponse;

		public GatewayRefundResponse(String gatewayRefundId, String status, String rawResponse) {
			this.gatewayRefundId = gatewayRefundId;
			this.status = status;
			this.rawResponse = rawResponse;
		}

		public String getGatewayRefundId() {
			return gatewayRefundId;
		}

		public String getStatus() {
			return status;
		}

		public String getRawResponse() {
			return rawResponse;
		}
	}
}