package com.schoolapp.payment.service;

import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.fee.entity.Invoice;
import com.schoolapp.fee.entity.InvoiceStatus;
import com.schoolapp.fee.repository.InvoiceRepository;
import com.schoolapp.notification.service.NotificationTriggerService;
import com.schoolapp.payment.dto.PaymentRequests.*;
import com.schoolapp.payment.dto.PaymentResponses.*;
import com.schoolapp.payment.entity.*;
import com.schoolapp.payment.gateway.PaymentGatewayFactory;
import com.schoolapp.payment.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.schoolapp.payment.gateway.PaymentGatewayService;
import com.schoolapp.audit.entity.AuditAction;
import com.schoolapp.audit.service.AuditLogService;
import com.schoolapp.common.security.SecurityUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentWebhookEventRepository webhookEventRepository;
    private final ReceiptRepository receiptRepository;
    private final RefundRepository refundRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentGatewayFactory paymentGatewayFactory;
    private final AuditLogService auditLogService;
    private final NotificationTriggerService notificationTriggerService;

    public PaymentService(PaymentOrderRepository paymentOrderRepository,
                          PaymentTransactionRepository paymentTransactionRepository,
                          PaymentWebhookEventRepository webhookEventRepository,
                          ReceiptRepository receiptRepository,
                          RefundRepository refundRepository,
                          InvoiceRepository invoiceRepository,
                          PaymentGatewayFactory paymentGatewayFactory,
                          AuditLogService auditLogService,
                          NotificationTriggerService notificationTriggerService) {
        this.paymentOrderRepository = paymentOrderRepository;
        this.paymentTransactionRepository = paymentTransactionRepository;
        this.webhookEventRepository = webhookEventRepository;
        this.receiptRepository = receiptRepository;
        this.refundRepository = refundRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentGatewayFactory = paymentGatewayFactory;
        this.auditLogService = auditLogService;
        this.notificationTriggerService = notificationTriggerService;
    }

    @Transactional
    public PaymentOrderResponse createOrder(CreatePaymentOrderRequest request) {
        Invoice invoice = findInvoice(request.getInvoiceId());

        if (invoice.getDueAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Invoice has no due amount");
        }

        String orderNumber = generateOrderNumber();

        PaymentGatewayService gatewayService =
                paymentGatewayFactory.getGateway(request.getGatewayName());

        PaymentGatewayService.GatewayOrderResponse gatewayResponse =
                gatewayService.createOrder(
                        new PaymentGatewayService.GatewayOrderRequest(
                                orderNumber,
                                invoice.getDueAmount(),
                                "INR",
                                null,
                                null,
                                null,
                                request.getPreferredPaymentMethod()
                        )
                );

        PaymentOrder order = new PaymentOrder();
        order.setOrderNumber(orderNumber);
        order.setInvoiceId(invoice.getId());
        order.setStudentId(invoice.getStudentId());
        order.setAmount(invoice.getDueAmount());
        order.setCurrency("INR");
        order.setGatewayName(request.getGatewayName());
        order.setGatewayOrderId(gatewayResponse.getGatewayOrderId());
        order.setCheckoutUrl(gatewayResponse.getCheckoutUrl());
        order.setPreferredPaymentMethod(request.getPreferredPaymentMethod());
        order.setStatus(PaymentStatus.CREATED);

        return toOrderResponse(paymentOrderRepository.save(order));
    }

    public PaymentOrderResponse getOrder(Long id) {
        return toOrderResponse(findOrder(id));
    }

    public List<PaymentTransactionResponse> getTransactions() {
        return paymentTransactionRepository.findAll()
                .stream()
                .map(this::toTransactionResponse)
                .toList();
    }

    @Transactional
    public PaymentTransactionResponse confirmPayment(ConfirmPaymentRequest request) {
        PaymentOrder order = findOrder(request.getPaymentOrderId());

        if (paymentTransactionRepository.findByGatewayPaymentId(request.getGatewayPaymentId()).isPresent()) {
            PaymentTransaction existing = paymentTransactionRepository
                    .findByGatewayPaymentId(request.getGatewayPaymentId())
                    .orElseThrow();
            return toTransactionResponse(existing);
        }

        if (request.getAmount().compareTo(order.getAmount()) != 0) {
            throw new RuntimeException("Payment amount does not match order amount");
        }

        PaymentTransaction transaction = new PaymentTransaction();
        transaction.setPaymentOrderId(order.getId());
        transaction.setGatewayPaymentId(request.getGatewayPaymentId());
        transaction.setGatewaySignature(request.getGatewaySignature());
        transaction.setAmount(request.getAmount());
        transaction.setStatus(PaymentStatus.SUCCESS);
        transaction.setPaymentMode(request.getPaymentMode());
        transaction.setRawResponse(request.getRawResponse());

        PaymentTransaction savedTransaction = paymentTransactionRepository.save(transaction);

        order.setStatus(PaymentStatus.SUCCESS);
        paymentOrderRepository.save(order);

        updateInvoiceAfterSuccessfulPayment(order);
        createReceiptIfNotExists(order.getInvoiceId(), savedTransaction);
        
        notificationTriggerService.sendPaymentSuccess(
                order.getStudentId(),
                savedTransaction.getAmount(),
                "RCPT-" + savedTransaction.getId()
        );
        
        // Audit successful payment
        auditLogService.log(
                AuditAction.PAYMENT_SUCCESS,
                "PAYMENT",
                "PaymentTransaction",
                savedTransaction.getId(),
                "Payment completed successfully for invoice id: " + order.getInvoiceId(),
                null,
                SecurityUtils.getCurrentUsername(),
                null,
                null
        );

        return toTransactionResponse(savedTransaction);
    }

    @Transactional
    public String handleWebhook(String provider, WebhookRequest request) {
        PaymentGatewayService gatewayService = paymentGatewayFactory.getGateway(provider);

        boolean valid = gatewayService.verifyWebhook(request.getPayload(), null);

        if (!valid) {
            throw new RuntimeException("Invalid webhook signature");
        }

        if (webhookEventRepository.existsByEventId(request.getEventId())) {
            return "Duplicate webhook ignored";
        }

        PaymentWebhookEvent event = new PaymentWebhookEvent();
        event.setGatewayName(provider);
        event.setEventId(request.getEventId());
        event.setEventType(request.getEventType());
        event.setPayload(request.getPayload());
        event.setProcessed(true);

        webhookEventRepository.save(event);

        return "Webhook recorded successfully";
    }

    public ReceiptResponse getReceipt(Long id) {
        Receipt receipt = receiptRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt not found with id: " + id));
        return toReceiptResponse(receipt);
    }

    @Transactional
    public RefundResponse initiateRefund(RefundRequest request) {
        PaymentTransaction transaction = paymentTransactionRepository.findById(request.getPaymentTransactionId())
                .orElseThrow(() -> new ResourceNotFoundException("Payment transaction not found"));

        if (transaction.getStatus() != PaymentStatus.SUCCESS) {
            throw new RuntimeException("Only successful payments can be refunded");
        }

        if (request.getRefundAmount().compareTo(transaction.getAmount()) > 0) {
            throw new RuntimeException("Refund amount cannot exceed transaction amount");
        }

        Refund refund = new Refund();
        refund.setPaymentTransactionId(transaction.getId());
        refund.setRefundAmount(request.getRefundAmount());
        refund.setReason(request.getReason());
        refund.setStatus(RefundStatus.REQUESTED);
        PaymentOrder order = findOrder(transaction.getPaymentOrderId());

        PaymentGatewayService gatewayService =
                paymentGatewayFactory.getGateway(order.getGatewayName());

        PaymentGatewayService.GatewayRefundResponse gatewayRefundResponse =
                gatewayService.initiateRefund(
                        new PaymentGatewayService.GatewayRefundRequest(
                                transaction.getGatewayPaymentId(),
                                request.getRefundAmount(),
                                request.getReason()
                        )
                );

        refund.setGatewayRefundId(gatewayRefundResponse.getGatewayRefundId());
        refund.setStatus(RefundStatus.PROCESSING);
        
        refund.setGatewayRefundId(gatewayRefundResponse.getGatewayRefundId());
        refund.setStatus(RefundStatus.PROCESSING);

        Refund savedRefund = refundRepository.save(refund);

        // AuditLog REFUND_REQUEST
        auditLogService.log(
                AuditAction.REFUND_REQUESTED,
                "PAYMENT",
                "Refund",
                savedRefund.getId(),
                "Refund requested for transaction id: " + transaction.getId(),
                null,
                SecurityUtils.getCurrentUsername(),
                null,
                null
        );

        return toRefundResponse(savedRefund);

    }

    public List<PaymentOrderResponse> getReconciliation() {
        return paymentOrderRepository.findAll()
                .stream()
                .map(this::toOrderResponse)
                .toList();
    }

    private void updateInvoiceAfterSuccessfulPayment(PaymentOrder order) {
        Invoice invoice = findInvoice(order.getInvoiceId());
        invoice.setPaidAmount(invoice.getPaidAmount().add(order.getAmount()));
        invoice.setDueAmount(invoice.getTotalAmount().subtract(invoice.getPaidAmount()));

        if (invoice.getDueAmount().compareTo(BigDecimal.ZERO) == 0) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else {
            invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
        }

        invoiceRepository.save(invoice);
    }

    private void createReceiptIfNotExists(Long invoiceId, PaymentTransaction transaction) {
        if (receiptRepository.findByPaymentTransactionId(transaction.getId()).isPresent()) {
            return;
        }

        Receipt receipt = new Receipt();
        receipt.setReceiptNumber(generateReceiptNumber());
        receipt.setInvoiceId(invoiceId);
        receipt.setPaymentTransactionId(transaction.getId());
        receipt.setAmount(transaction.getAmount());

        receiptRepository.save(receipt);
    }

    private Invoice findInvoice(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
    }

    private PaymentOrder findOrder(Long id) {
        return paymentOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment order not found with id: " + id));
    }

    private String generateOrderNumber() {
        return "PAY-ORD-" + System.currentTimeMillis();
    }

    private String generateReceiptNumber() {
        return "RCPT-" + System.currentTimeMillis();
    }

    private PaymentOrderResponse toOrderResponse(PaymentOrder order) {
        return new PaymentOrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getInvoiceId(),
                order.getStudentId(),
                order.getAmount(),
                order.getCurrency(),
                order.getGatewayName(),
                order.getGatewayOrderId(),
                order.getStatus(),
                order.getCheckoutUrl(),
                order.getPreferredPaymentMethod()
        );
    }

    private PaymentTransactionResponse toTransactionResponse(PaymentTransaction transaction) {
        return new PaymentTransactionResponse(
                transaction.getId(),
                transaction.getPaymentOrderId(),
                transaction.getGatewayPaymentId(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getPaymentMode(),
                transaction.getPaidAt()
        );
    }

    private ReceiptResponse toReceiptResponse(Receipt receipt) {
        return new ReceiptResponse(
                receipt.getId(),
                receipt.getReceiptNumber(),
                receipt.getInvoiceId(),
                receipt.getPaymentTransactionId(),
                receipt.getAmount(),
                receipt.getGeneratedAt()
        );
    }

    private RefundResponse toRefundResponse(Refund refund) {
        return new RefundResponse(
                refund.getId(),
                refund.getPaymentTransactionId(),
                refund.getRefundAmount(),
                refund.getStatus(),
                refund.getReason()
        );
    }
}