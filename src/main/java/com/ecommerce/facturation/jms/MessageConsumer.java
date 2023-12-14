package com.ecommerce.facturation.jms;

import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dto.InvoiceDTO;
import com.ecommerce.facturation.service.facade.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.Supplier;

@Component
public class MessageConsumer {

    @Autowired
    private InvoiceService invoiceService;

//    private static int invoiceThreadIndex = 0;
//    static ThreadFactory invoiceThreadFactory = (r) -> new Thread(r, "Invoice-" + invoiceThreadIndex++);

    @Async
    @JmsListener(destination = "commandeBilling2", containerFactory = "jmsListenerContainerFactory", concurrency = "1000")
    public void receiveMessage(Message<String> orderDto) {
        System.out.println("Current Thread in receiveMessage: " + Thread.currentThread());
        System.out.println("Message reçu du topic : " + orderDto);
        String payload = orderDto.getPayload();
        invoiceService.setDataToInvoice(payload);

//        Supplier<InvoiceDTO> saveInvoice =
//                () -> {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException();
//                    }
//                    InvoiceDTO invoiceDTO = invoiceService.setDataToInvoice(payload);
//                    System.out.println("Invoice" + invoiceDTO.invoiceId() + "running in " + Thread.currentThread());
//                    return invoiceDTO;
//                };
//
//        ExecutorService invoiceExecutorService = Executors.newFixedThreadPool(12, invoiceThreadFactory);
//
//        CompletableFuture.supplyAsync(saveInvoice, invoiceExecutorService);


    }

    //    @JmsListener(destination = "commandeBillingCOD", containerFactory = "jmsListenerContainerFactory", concurrency = "6")
    public void receiveMessageOrderPaid(Message<String> orderDto) {
        System.out.println("Current Thread in receiveMessage: " + Thread.currentThread());
        System.out.println("Message reçu du topic : " + orderDto);
        String payload = orderDto.getPayload();
        invoiceService.setDataToInvoiceUpdate(payload).join();
    }


}
