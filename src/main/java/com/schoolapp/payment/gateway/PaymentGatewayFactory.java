package com.schoolapp.payment.gateway;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentGatewayFactory {

    private final List<PaymentGatewayService> gatewayServices;

    public PaymentGatewayFactory(List<PaymentGatewayService> gatewayServices) {
        this.gatewayServices = gatewayServices;
    }

    public PaymentGatewayService getGateway(String gatewayName) {
        return gatewayServices.stream()
                .filter(service -> service.getGatewayName().equalsIgnoreCase(gatewayName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unsupported payment gateway: " + gatewayName));
    }
}