package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.Enum.InvoiceStatus;
import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dao.InvoiceDao;
import com.ecommerce.facturation.dto.*;
import com.ecommerce.facturation.mapper.InvoiceMapper;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.ecommerce.facturation.service.facade.InvoiceService;
import com.ecommerce.facturation.utils.invoicePdf.model.AddressDetails;
import com.ecommerce.facturation.utils.invoicePdf.model.HeaderDetails;
import com.ecommerce.facturation.utils.invoicePdf.model.Product;
import com.ecommerce.facturation.utils.invoicePdf.model.ProductTableHeader;
import com.ecommerce.facturation.utils.invoicePdf.service.CodingErrorPdfInvoiceCreator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private InvoiceMapper invoiceMapper;

    public void setInvoiceMapper(InvoiceMapper invoiceMapper) {
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public List<InvoiceDTO> getInvoices() {
        return invoiceDao.findAll().stream().map(invoice -> invoiceMapper.fromInvoice(invoice)).collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(Long id) {
        Invoice invoice = invoiceDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found with id: " + id));
        return invoiceMapper.fromInvoice(invoice);
    }

    @Override
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice savedInvoice = invoiceDao.save(invoice);
        try {
            generatePdfForInvoiceAndSend(savedInvoice);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        sendInvoiceEmail(invoice);
        return invoiceMapper.fromInvoice(savedInvoice);
    }

    @Autowired
    private JsonMapper jsonMapper;

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;


    @Autowired
    private JavaMailSender emailSender;
//    @Autowired
//    private TemplateEngine templateEngine;


    public InvoiceDTO save(String payload) {
        OrderDto orderDto = jsonMapper.convertJsonToObject(payload, OrderDto.class);
        ClientDTO clientDTO = jsonMapper.convertJsonToObject(orderDto.client(), ClientDTO.class);
        List<CommandItemDto> commandItemDtos = jsonMapper.convertJsonToObjects(orderDto.commandItemDtos(), CommandItemDto.class);
        InvoiceDTO invoiceDTO = new InvoiceDTO(
                orderDto.reference(),
                LocalDateTime.now().plusDays(30),
                InvoiceStatus.Paid,
                orderDto.totalPay(),
                clientDTO,
                commandItemDtos
        );
        return save(invoiceDTO);
    }

    private void generatePdfForInvoiceAndSend(Invoice invoice) throws FileNotFoundException {
        String pdfName = invoice.getInvoiceNumber() + ".pdf";
        CodingErrorPdfInvoiceCreator cepdf = new CodingErrorPdfInvoiceCreator(pdfName);
        cepdf.createDocument();

        //Create Header start
        HeaderDetails header = new HeaderDetails();
        header.setInvoiceNo(invoice.getInvoiceNumber())
                .setInvoiceDate(invoice.getDueDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                .build();
        cepdf.createHeader(header);
        //Header End

        //Create Address start
        AddressDetails addressDetails = new AddressDetails();
        addressDetails
                .setBillingCompany("UIR-SHOP")
                .setBillingName("Name Of Provider")
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
            Product product = new Product(Optional.of(productDto.name()), itemDto.quantity(), productDto.unitPrice(), itemDto.amount());
            productList.add(product);
        }
        productList = cepdf.modifyProductList(productList);
        cepdf.createProduct(productList);

        //Product End

        //Term and Condition Start
        String generalePath = "src/main/resources/invoices/";
        List<String> TncList = new ArrayList<>();
        TncList.add("1. The Seller shall not be liable to the Buyer directly or indirectly for any loss or damage suffered by the Buyer.");
        TncList.add("2. The Seller warrants the product for one (1) year from the date of shipment");
        String imagePath = generalePath + "UIR.jpg";
        cepdf.createTnc(TncList, false, imagePath);
        // Term and condition end
        System.out.println("pdf genrated");

    }


    private void sendInvoiceEmail(Invoice invoice) {
        try {
//            Thread.sleep(10000);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setPriority(1);
            helper.setSubject("Invoice Order");
            helper.setFrom(fromEmail);
            helper.setTo(invoice.getClientEmail());
            helper.setText("Text");
            // Add attachments
            FileSystemResource pdf = new FileSystemResource(new File("src/main/resources/invoices/" + invoice.getInvoiceNumber() + ".pdf"));
            helper.addInline(getContentId(pdf.getFilename()), pdf);

            emailSender.send(message);


        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }

    private String getContentId(String fileName) {
        return "<" + fileName + ">";
    }

    @Override
    public InvoiceDTO update(InvoiceDTO invoiceDTO) {
        findById(invoiceDTO.invoiceId());
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice updatedInvoice = invoiceDao.save(invoice);
        return invoiceMapper.fromInvoice(updatedInvoice);
    }

    @Override
    public boolean deleteInvoiceById(Long id) {
        InvoiceDTO invoiceDTO = findById(id);
        if (invoiceDTO != null) {
            invoiceDao.deleteById(id);
            return true;
        } else return false;
    }

//    @Autowired
//    private JsonMapper jsonMapper;
//
//    public OrderDto receiveOrder(String jsonMessage) throws JsonProcessingException, ClassNotFoundException {
//        OrderDto orderDto = jsonMapper.convertJsonToObject(jsonMessage, OrderDto.class);
//        return orderDto;
//    }


}
