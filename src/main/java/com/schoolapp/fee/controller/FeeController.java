package com.schoolapp.fee.controller;

import com.schoolapp.common.response.ApiResponse;
import com.schoolapp.fee.dto.FeeRequests.*;
import com.schoolapp.fee.dto.FeeResponses.*;
import com.schoolapp.fee.entity.*;
import com.schoolapp.fee.service.FeeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fees")
public class FeeController {

    private final FeeService feeService;

    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping("/categories")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<FeeCategory> createCategory(@Valid @RequestBody CreateFeeCategoryRequest request) {
        return ApiResponse.success("Fee category created successfully", feeService.createCategory(request));
    }

    @GetMapping("/categories")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<List<FeeCategory>> getCategories() {
        return ApiResponse.success("Fee categories fetched successfully", feeService.getCategories());
    }

    @PostMapping("/structures")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<FeeStructure> createStructure(@Valid @RequestBody CreateFeeStructureRequest request) {
        return ApiResponse.success("Fee structure created successfully", feeService.createStructure(request));
    }

    @GetMapping("/structures")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<List<FeeStructure>> getStructures(
            @RequestParam(required = false) Long academicYearId,
            @RequestParam(required = false) Long classId
    ) {
        return ApiResponse.success("Fee structures fetched successfully", feeService.getStructures(academicYearId, classId));
    }

    @PostMapping("/invoices")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<InvoiceResponse> createInvoice(@Valid @RequestBody CreateInvoiceRequest request) {
        return ApiResponse.success("Invoice created successfully", feeService.createInvoice(request));
    }

    @GetMapping("/invoices")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT', 'PARENT', 'STUDENT')")
    public ApiResponse<List<InvoiceResponse>> getInvoices(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) InvoiceStatus status
    ) {
        return ApiResponse.success("Invoices fetched successfully", feeService.getInvoices(studentId, status));
    }

    @GetMapping("/invoices/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT', 'PARENT', 'STUDENT')")
    public ApiResponse<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        return ApiResponse.success("Invoice fetched successfully", feeService.getInvoiceById(id));
    }

    @PostMapping("/payments/offline")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<FeePaymentResponse> recordOfflinePayment(
            @Valid @RequestBody RecordOfflinePaymentRequest request
    ) {
        return ApiResponse.success("Offline payment recorded successfully", feeService.recordOfflinePayment(request));
    }

    @GetMapping("/reports/collection")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<FeeReportResponse> getCollectionReport() {
        return ApiResponse.success("Fee collection report fetched successfully", feeService.getCollectionReport());
    }

    @GetMapping("/reports/dues")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRINCIPAL', 'ACCOUNTANT')")
    public ApiResponse<List<InvoiceResponse>> getDuesReport() {
        return ApiResponse.success("Fee dues report fetched successfully", feeService.getDuesReport());
    }
}