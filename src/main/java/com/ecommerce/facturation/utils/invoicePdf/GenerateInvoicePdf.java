package com.ecommerce.facturation.utils.invoicePdf;

import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dto.CommandItemDto;
import com.ecommerce.facturation.dto.ProductDto;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.ecommerce.facturation.utils.invoicePdf.model.AddressDetails;
import com.ecommerce.facturation.utils.invoicePdf.model.HeaderDetails;
import com.ecommerce.facturation.utils.invoicePdf.model.Product;
import com.ecommerce.facturation.utils.invoicePdf.model.ProductTableHeader;
import com.ecommerce.facturation.utils.invoicePdf.service.CodingErrorPdfInvoiceCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GenerateInvoicePdf {

    @Autowired
    private JsonMapper jsonMapper;

    @Value("${spring.user.pdf}")
    String generalePath;

    public void generate(Invoice invoice) throws FileNotFoundException {
        String pdfName = invoice.getInvoiceNumber() + ".pdf";
        CodingErrorPdfInvoiceCreator cepdf = new CodingErrorPdfInvoiceCreator(pdfName);
        cepdf.createDocument();

        //Create Header start
        HeaderDetails header = new HeaderDetails();
        header.setInvoiceNo(invoice.getInvoiceNumber())
                .setInvoiceDate(invoice.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .build();
        cepdf.createHeader(header);
        //Header End

        //Create Address start
        AddressDetails addressDetails = new AddressDetails();
        addressDetails
                .setBillingCompany("UIR-SHOP")
                .setBillingName("name.........")
                .setBillingAddress("RABAT Technopolis")
                .setBillingEmail(invoice.getClientEmail())
                .setShippingName(invoice.getClientName())
                .setShippingAddress(invoice.getClientAddress())
                .build();

        cepdf.createAddress(addressDetails);
        //Address end

        //Product Start
        ProductTableHeader productTableHeader = new ProductTableHeader();
        cepdf.createTableHeader(productTableHeader);
        List<Product> productList = new ArrayList<>();
        List<CommandItemDto> commandItemDtos = jsonMapper.convertJsonToObjects(invoice.getProducts(), CommandItemDto.class);
        for (CommandItemDto itemDto : commandItemDtos) {
            ProductDto productDto = jsonMapper.convertJsonToObject(itemDto.product(), ProductDto.class);
            Product product = new Product(Optional.of(productDto.name()), itemDto.quantity(), itemDto.amount(), itemDto.amount());
            productList.add(product);
        }
        productList = cepdf.modifyProductList(productList);
        cepdf.createProduct(productList);

        //Product End


        //Term and Condition Start

        List<String> TncList = new ArrayList<>();
        TncList.add("1. The Seller shall not be liable to the Buyer directly or indirectly for any loss or damage suffered by the Buyer.");
        TncList.add("2. The Seller warrants the product for one (1) year from the date of shipment");
        String imagePath = "pdf/" + "UIR.jpg";
        cepdf.createTnc(TncList, false, imagePath);
        // Term and condition end
        System.out.println("pdf genrated");

    }

}
