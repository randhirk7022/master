package com.schoolapp.payment.gateway;

import org.springframework.stereotype.Service;

@Service
public class PhonePePaymentGatewayService implements PaymentGatewayService {

    @Override
    public GatewayOrderResponse createOrder(GatewayOrderRequest request) {
        String gatewayOrderId = "phonepe_order_" + System.currentTimeMillis();

        return new GatewayOrderResponse(
                gatewayOrderId,
                "https://phonepe.example.com/pay/" + gatewayOrderId,
                "{\"gateway\":\"PHONEPE\",\"status\":\"created\"}"
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
                "phonepe_refund_" + System.currentTimeMillis(),
                "REQUESTED",
                "{\"gateway\":\"PHONEPE\",\"refund\":\"requested\"}"
        );
    }

    @Override
    public String getGatewayName() {
        return "PHONEPE";
    }
}