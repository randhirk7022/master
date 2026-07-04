package com.schoolapp.payment.gateway;

import org.springframework.stereotype.Service;

@Service
public class PayUPaymentGatewayService implements PaymentGatewayService {

    @Override
    public GatewayOrderResponse createOrder(GatewayOrderRequest request) {
        String gatewayOrderId = "payu_order_" + System.currentTimeMillis();

        return new GatewayOrderResponse(
                gatewayOrderId,
                "https://payu.example.com/checkout/" + gatewayOrderId,
                "{\"gateway\":\"PAYU\",\"status\":\"created\"}"
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
                "payu_refund_" + System.currentTimeMillis(),
                "REQUESTED",
                "{\"gateway\":\"PAYU\",\"refund\":\"requested\"}"
        );
    }

    @Override
    public String getGatewayName() {
        return "PAYU";
    }
}