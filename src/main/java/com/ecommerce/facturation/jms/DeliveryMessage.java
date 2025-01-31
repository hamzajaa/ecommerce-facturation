package com.ecommerce.facturation.jms;

import com.ecommerce.facturation.exception.BankAccountNotFoundException;
import com.ecommerce.facturation.service.facade.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMessage {

    //    private static int bankAccountThreadIndex = 0;
//    static ThreadFactory bankAccountThreadFactory = (r) -> new Thread(r, "BankAccountD-" + bankAccountThreadIndex++);


//    @Async
////    @JmsListener(destination = "deliveryPersonneFacture", containerFactory = "jmsListenerContainerFactory", concurrency = "3")
//    public void receiveMessage(Message<String> orderDto) {
//        System.out.println("Current Thread in receiveMessage: " + Thread.currentThread());
//        System.out.println("Message reçu du topic : " + orderDto);
//        String payload = orderDto.getPayload();
//        bankAccountService.setDataDeliveryManToBankAccount(payload).join();
//
////        ExecutorService bankAccountExecutorService = Executors.newFixedThreadPool(4, bankAccountThreadFactory);
////
////        CompletableFuture.supplyAsync(() -> {
////            return bankAccountService.setDataToBankAccount(payload);
////        }, bankAccountExecutorService);
//    }

    @Autowired
    private InvoiceService invoiceService;

    @Async
    @JmsListener(destination = "CommandeStatusTopic", containerFactory = "jmsListenerTopicContainerFactory", concurrency = "1000")
    public void receiveMessage(Message<String> orderDto) throws BankAccountNotFoundException {
        System.out.println("Current Thread in receiveMessage: " + Thread.currentThread());
        System.out.println("Message reçu du topic : " + orderDto);
        String payload = orderDto.getPayload();
        invoiceService.setDataToInvoiceAndUpdate(payload);

//        ExecutorService bankAccountExecutorService = Executors.newFixedThreadPool(4, bankAccountThreadFactory);
//
//        CompletableFuture.supplyAsync(() -> {
//            return bankAccountService.setDataToBankAccount(payload);
//        }, bankAccountExecutorService);
    }

}
