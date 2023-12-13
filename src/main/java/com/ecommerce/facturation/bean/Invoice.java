package com.ecommerce.facturation.bean;

import com.ecommerce.facturation.Enum.InvoiceStatus;
import com.ecommerce.facturation.Enum.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends AbstractSupperClass {

    private String orderReference;

    private String invoiceNumber;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private BigDecimal totalPay;

    private String clientName;

    private String clientAddress;

    private String clientNumberPhone;

    private String clientEmail;

    @Column(length = 100_000)
    private String products;

    @PrePersist
    private void generateInvoiceNumber() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = LocalDateTime.now().format(formatter);

        Random random = new Random();
        int randomComponent = random.nextInt(1000);

        this.invoiceNumber = timestamp + String.format("%03d", randomComponent);
    }


//    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//
//    @PrePersist
//    private void generateInvoiceNumber() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
//        String timestamp = LocalDateTime.now().format(formatter);
//
//        String stringGenerated = generateRandomComponent(3);
//
//        this.invoiceNumber = timestamp + stringGenerated;
//
//    }
//
//    private String generateRandomComponent(int length) {
//        List<Character> characters = new ArrayList<>();
//        for (char c : ALPHABET.toCharArray()) {
//            characters.add(c);
//        }
//
//        Collections.shuffle(characters);
//
//        StringBuilder randomString = new StringBuilder();
//        Random random = new Random();
//
//        for (int i = 0; i < length; i++) {
//            randomString.append(characters.get(random.nextInt(ALPHABET.length())));
//        }
//
//        return randomString.toString();
//    }

}
