package com.ecommerce.facturation.bean;

import com.ecommerce.facturation.Enum.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Invoice extends AbstractSupperClass {


    private String orderReference;

    private String invoiceNumber = UUID.randomUUID().toString();

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    private BigDecimal totalPay;

    private String clientName;

    private String clientAddress;

    private String clientNumberPhone;

    private String clientEmail;

    @Column(length = 100_000)
    private String products;


}
