package com.schoolapp.fee.dto;

import com.schoolapp.fee.entity.FeePaymentMode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FeeRequests {

    public static class CreateFeeCategoryRequest {
        @NotNull
        private String name;
        private String description;

        public String getName() { return name; }
        public String getDescription() { return description; }
    }

    public static class CreateFeeStructureRequest {
        @NotNull
        private Long academicYearId;
        @NotNull
        private Long classId;
        @NotNull
        private Long categoryId;
        @NotNull
        private BigDecimal amount;
        private LocalDate dueDate;

        public Long getAcademicYearId() { return academicYearId; }
        public Long getClassId() { return classId; }
        public Long getCategoryId() { return categoryId; }
        public BigDecimal getAmount() { return amount; }
        public LocalDate getDueDate() { return dueDate; }
    }

    public static class CreateInvoiceRequest {
        @NotNull
        private Long studentId;
        private Long academicYearId;
        private Long classId;
        private LocalDate dueDate;

        @Valid
        @NotEmpty
        private List<CreateInvoiceItemRequest> items;

        public Long getStudentId() { return studentId; }
        public Long getAcademicYearId() { return academicYearId; }
        public Long getClassId() { return classId; }
        public LocalDate getDueDate() { return dueDate; }
        public List<CreateInvoiceItemRequest> getItems() { return items; }
    }

    public static class CreateInvoiceItemRequest {
        @NotNull
        private Long feeCategoryId;
        @NotNull
        private BigDecimal amount;

        public Long getFeeCategoryId() { return feeCategoryId; }
        public BigDecimal getAmount() { return amount; }
    }

    public static class RecordOfflinePaymentRequest {
        @NotNull
        private Long invoiceId;
        @NotNull
        private BigDecimal amount;
        @NotNull
        private FeePaymentMode paymentMode;
        private String referenceNumber;
        private String remarks;

        public Long getInvoiceId() { return invoiceId; }
        public BigDecimal getAmount() { return amount; }
        public FeePaymentMode getPaymentMode() { return paymentMode; }
        public String getReferenceNumber() { return referenceNumber; }
        public String getRemarks() { return remarks; }
    }
}