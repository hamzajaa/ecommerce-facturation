package com.ecommerce.facturation.utils.invoicePdf;

import com.ecommerce.facturation.bean.Invoice;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class SendEmailToClient {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${spring.user.pdf}")
    String resourcePath;

    public void send(Invoice invoice) {
        CompletableFuture.runAsync(() -> {
            try {
                //            Thread.sleep(10000);
                MimeMessage message = getMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                helper.setPriority(1);
                helper.setSubject("Invoice for Your Recent Order - Order #" + invoice.getOrderReference());
                helper.setFrom(fromEmail);
                helper.setTo(invoice.getClientEmail());
                helper.setText("Please find attached the invoice for your recent order. The invoice contains all the necessary details, including the order number, date, due date, and total amount.\n");
                // Add attachments
                FileSystemResource pdf = new FileSystemResource(new File("pdf/" + invoice.getInvoiceNumber() + ".pdf"));
                //            helper.addInline(getContentId(pdf.getFilename()), pdf);
                helper.addAttachment("Invoice.pdf", pdf);

                emailSender.send(message);
                log.info("PDF Invoice send it successfully. ID {}", invoice.getId());

                // Remove the PDF file after sending the email
                removePdfFile(invoice.getInvoiceNumber());

            } catch (MessagingException e) {
                log.error("Error sending pdf Invoice: " + e.getMessage());
            }
        });
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }

    private String getContentId(String fileName) {
        return "<" + fileName + ">";
    }

    private void removePdfFile(String invoiceNumber) {
        try {
            Path pdfFilePath = Paths.get("pdf/" + invoiceNumber + ".pdf");
            Files.delete(pdfFilePath);
            log.info("PDF Invoice removed: " + pdfFilePath);
        } catch (IOException e) {
            log.error("Error deleting Pdf Invoice: " + e.getMessage());
        }
    }
}
