package com.schoolapp.fee.service;

import com.schoolapp.common.exception.ResourceNotFoundException;
import com.schoolapp.fee.dto.FeeRequests.*;
import com.schoolapp.fee.dto.FeeResponses.*;
import com.schoolapp.fee.entity.*;
import com.schoolapp.fee.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class FeeService {

    private final FeeCategoryRepository feeCategoryRepository;
    private final FeeStructureRepository feeStructureRepository;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final FeePaymentRepository feePaymentRepository;

    public FeeService(FeeCategoryRepository feeCategoryRepository,
                      FeeStructureRepository feeStructureRepository,
                      InvoiceRepository invoiceRepository,
                      InvoiceItemRepository invoiceItemRepository,
                      FeePaymentRepository feePaymentRepository) {
        this.feeCategoryRepository = feeCategoryRepository;
        this.feeStructureRepository = feeStructureRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.feePaymentRepository = feePaymentRepository;
    }

    public FeeCategory createCategory(CreateFeeCategoryRequest request) {
        if (feeCategoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Fee category already exists");
        }

        FeeCategory category = new FeeCategory();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return feeCategoryRepository.save(category);
    }

    public List<FeeCategory> getCategories() {
        return feeCategoryRepository.findAll();
    }

    public FeeStructure createStructure(CreateFeeStructureRequest request) {
        FeeStructure structure = new FeeStructure();
        structure.setAcademicYearId(request.getAcademicYearId());
        structure.setClassId(request.getClassId());
        structure.setCategoryId(request.getCategoryId());
        structure.setAmount(request.getAmount());
        structure.setDueDate(request.getDueDate());
        return feeStructureRepository.save(structure);
    }

    public List<FeeStructure> getStructures(Long academicYearId, Long classId) {
        if (academicYearId != null && classId != null) {
            return feeStructureRepository.findByAcademicYearIdAndClassId(academicYearId, classId);
        }

        if (classId != null) {
            return feeStructureRepository.findByClassId(classId);
        }

        return feeStructureRepository.findAll();
    }

    @Transactional
    public InvoiceResponse createInvoice(CreateInvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setStudentId(request.getStudentId());
        invoice.setAcademicYearId(request.getAcademicYearId());
        invoice.setClassId(request.getClassId());
        invoice.setDueDate(request.getDueDate());

        BigDecimal totalAmount = request.getItems()
                .stream()
                .map(CreateInvoiceItemRequest::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        invoice.setTotalAmount(totalAmount);
        invoice.setPaidAmount(BigDecimal.ZERO);
        invoice.setDueAmount(totalAmount);
        invoice.setStatus(InvoiceStatus.UNPAID);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<InvoiceItem> items = request.getItems().stream().map(itemRequest -> {
            InvoiceItem item = new InvoiceItem();
            item.setInvoiceId(savedInvoice.getId());
            item.setFeeCategoryId(itemRequest.getFeeCategoryId());
            item.setAmount(itemRequest.getAmount());
            return item;
        }).toList();

        invoiceItemRepository.saveAll(items);
        return toInvoiceResponse(savedInvoice);
    }

    public List<InvoiceResponse> getInvoices(Long studentId, InvoiceStatus status) {
        List<Invoice> invoices;

        if (studentId != null) {
            invoices = invoiceRepository.findByStudentId(studentId);
        } else if (status != null) {
            invoices = invoiceRepository.findByStatus(status);
        } else {
            invoices = invoiceRepository.findAll();
        }

        return invoices.stream().map(this::toInvoiceResponse).toList();
    }

    public InvoiceResponse getInvoiceById(Long id) {
        return toInvoiceResponse(findInvoice(id));
    }

    @Transactional
    public FeePaymentResponse recordOfflinePayment(RecordOfflinePaymentRequest request) {
        Invoice invoice = findInvoice(request.getInvoiceId());

        if (invoice.getStatus() == InvoiceStatus.CANCELLED) {
            throw new RuntimeException("Cannot record payment for cancelled invoice");
        }

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }

        if (request.getAmount().compareTo(invoice.getDueAmount()) > 0) {
            throw new RuntimeException("Payment amount cannot be greater than due amount");
        }

        FeePayment payment = new FeePayment();
        payment.setInvoiceId(invoice.getId());
        payment.setStudentId(invoice.getStudentId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMode(request.getPaymentMode());
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setRemarks(request.getRemarks());

        FeePayment savedPayment = feePaymentRepository.save(payment);

        invoice.setPaidAmount(invoice.getPaidAmount().add(request.getAmount()));
        invoice.setDueAmount(invoice.getTotalAmount().subtract(invoice.getPaidAmount()));
        updateInvoiceStatus(invoice);
        invoiceRepository.save(invoice);

        return toPaymentResponse(savedPayment);
    }

    public FeeReportResponse getCollectionReport() {
        List<Invoice> invoices = invoiceRepository.findAll();

        BigDecimal totalAmount = invoices.stream()
                .map(Invoice::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal paidAmount = invoices.stream()
                .map(Invoice::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal dueAmount = invoices.stream()
                .map(Invoice::getDueAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new FeeReportResponse(totalAmount, paidAmount, dueAmount);
    }

    public List<InvoiceResponse> getDuesReport() {
        return invoiceRepository.findAll()
                .stream()
                .filter(invoice -> invoice.getDueAmount().compareTo(BigDecimal.ZERO) > 0)
                .map(this::toInvoiceResponse)
                .toList();
    }

    private Invoice findInvoice(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
    }

    private void updateInvoiceStatus(Invoice invoice) {
        if (invoice.getDueAmount().compareTo(BigDecimal.ZERO) == 0) {
            invoice.setStatus(InvoiceStatus.PAID);
        } else if (invoice.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            invoice.setStatus(InvoiceStatus.PARTIALLY_PAID);
        } else if (invoice.getDueDate() != null && invoice.getDueDate().isBefore(LocalDate.now())) {
            invoice.setStatus(InvoiceStatus.OVERDUE);
        } else {
            invoice.setStatus(InvoiceStatus.UNPAID);
        }
    }

    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
    }

    private InvoiceResponse toInvoiceResponse(Invoice invoice) {
        List<InvoiceItemResponse> items = invoiceItemRepository.findByInvoiceId(invoice.getId())
                .stream()
                .map(item -> new InvoiceItemResponse(item.getId(), item.getFeeCategoryId(), item.getAmount()))
                .toList();

        return new InvoiceResponse(
                invoice.getId(),
                invoice.getInvoiceNumber(),
                invoice.getStudentId(),
                invoice.getTotalAmount(),
                invoice.getPaidAmount(),
                invoice.getDueAmount(),
                invoice.getDueDate(),
                invoice.getStatus(),
                items
        );
    }

    private FeePaymentResponse toPaymentResponse(FeePayment payment) {
        return new FeePaymentResponse(
                payment.getId(),
                payment.getInvoiceId(),
                payment.getStudentId(),
                payment.getAmount(),
                payment.getPaymentMode(),
                payment.getReferenceNumber(),
                payment.getPaidAt()
        );
    }
}