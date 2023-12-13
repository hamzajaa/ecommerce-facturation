package com.ecommerce.facturation.utils.invoicePdf.model;

import com.ecommerce.facturation.utils.invoicePdf.util.ConstantUtil;
import com.itextpdf.kernel.color.Color;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HeaderDetails {
    String invoiceTitle = ConstantUtil.INVOICE_TITLE;
    String invoiceNoText = ConstantUtil.INVOICE_NO_TEXT;
    String invoiceDateText = ConstantUtil.INVOICE_DATE_TEXT;
    String invoiceNumber = ConstantUtil.EMPTY;
    String invoiceDate = ConstantUtil.EMPTY;
    String invoiceStatusText = ConstantUtil.INVOICE_STATUS_TEXT;
    String paymentMethodText = ConstantUtil.PAYMENT_METHOD_TEXT;
    String invoiceStatus = ConstantUtil.EMPTY;
    String paymentMethod = ConstantUtil.EMPTY;


    Color borderColor = Color.GRAY;

    public HeaderDetails setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
        return this;
    }

    public HeaderDetails setInvoiceNoText(String invoiceNoText) {
        this.invoiceNoText = invoiceNoText;
        return this;
    }

    public HeaderDetails setInvoiceDateText(String invoiceDateText) {
        this.invoiceDateText = invoiceDateText;
        return this;
    }

    public HeaderDetails setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public HeaderDetails setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public HeaderDetails setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
        return this;
    }

    public HeaderDetails setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public HeaderDetails setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }


    public HeaderDetails build() {
        return this;
    }
}
