package com.schoolapp.payment.gateway;

import org.springframework.stereotype.Service;

@Service
public class RazorpayPaymentGatewayService implements PaymentGatewayService {

    @Override
    public GatewayOrderResponse createOrder(GatewayOrderRequest request) {
        String gatewayOrderId = "razorpay_order_" + System.currentTimeMillis();

        return new GatewayOrderResponse(
                gatewayOrderId,
                null,
                "{\"gateway\":\"RAZORPAY\",\"status\":\"created\"}"
        );
    }

    @Override
    public boolean verifyWebhook(String payload, String signature) {
        return true;
    }

    @Override
    public boolean verifyPaymentSignature(String orderId, String paymentId, String signature) {
        return true;
    }

    @Override
    public GatewayRefundResponse initiateRefund(GatewayRefundRequest request) {
        return new GatewayRefundResponse(
                "razorpay_refund_" + System.currentTimeMillis(),
                "REQUESTED",
                "{\"gateway\":\"RAZORPAY\",\"refund\":\"requested\"}"
        );
    }

    @Override
    public String getGatewayName() {
        return "RAZORPAY";
    }
}