package com.ecommerce.facturation.utils.invoicePdf.model;

import com.ecommerce.facturation.utils.invoicePdf.util.ConstantUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductTableHeader {

    String description = ConstantUtil.PRODUCT_TABLE_DESCRIPTION;
    String quantity = ConstantUtil.PRODUCT_TABLE_QUANTITY;
    String unitPrice = ConstantUtil.PRODUCT_TABLE_UNIT_PRICE;
    String amount = ConstantUtil.PRODUCT_TABLE_AMOUNT;

    public ProductTableHeader setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductTableHeader setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public ProductTableHeader setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public ProductTableHeader setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public ProductTableHeader build() {
        return this;
    }
}
