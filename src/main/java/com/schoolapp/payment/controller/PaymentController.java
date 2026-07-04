package com.schoolapp.payment.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.payment.dto.PaymentRequests.*;
import com.schoolapp.payment.dto.PaymentResponses.*;
import com.schoolapp.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/orders")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT', 'PARENT', 'STUDENT')")
    public ApiResponse<PaymentOrderResponse> createOrder(
            @Valid @RequestBody CreatePaymentOrderRequest request
    ) {
        return ApiResponse.success("Payment order created successfully", paymentService.createOrder(request));
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT', 'PARENT', 'STUDENT')")
    public ApiResponse<PaymentOrderResponse> getOrder(@PathVariable Long id) {
        return ApiResponse.success("Payment order fetched successfully", paymentService.getOrder(id));
    }

    @PostMapping("/confirm")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<PaymentTransactionResponse> confirmPayment(
            @Valid @RequestBody ConfirmPaymentRequest request
    ) {
        return ApiResponse.success("Payment confirmed successfully", paymentService.confirmPayment(request));
    }

    @PostMapping("/webhooks/{provider}")
    public ApiResponse<String> handleWebhook(
            @PathVariable String provider,
            @Valid @RequestBody WebhookRequest request
    ) {
        return ApiResponse.success("Webhook processed", paymentService.handleWebhook(provider, request));
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<List<PaymentTransactionResponse>> getTransactions() {
        return ApiResponse.success("Payment transactions fetched successfully", paymentService.getTransactions());
    }

    @GetMapping("/receipts/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT', 'PARENT', 'STUDENT')")
    public ApiResponse<ReceiptResponse> getReceipt(@PathVariable Long id) {
        return ApiResponse.success("Receipt fetched successfully", paymentService.getReceipt(id));
    }

    @PostMapping("/refunds")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<RefundResponse> initiateRefund(@Valid @RequestBody RefundRequest request) {
        return ApiResponse.success("Refund requested successfully", paymentService.initiateRefund(request));
    }

    @GetMapping("/reconciliation")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<List<PaymentOrderResponse>> getReconciliation() {
        return ApiResponse.success("Payment reconciliation fetched successfully", paymentService.getReconciliation());
    }
}