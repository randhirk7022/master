package com.schoolapp.fee.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_items")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long invoiceId;
    private Long feeCategoryId;

    @Column(nullable = false)
    private BigDecimal amount;

    public Long getId() { return id; }
    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }
    public Long getFeeCategoryId() { return feeCategoryId; }
    public void setFeeCategoryId(Long feeCategoryId) { this.feeCategoryId = feeCategoryId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}