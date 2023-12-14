package com.ecommerce.facturation.jms;


import com.ecommerce.facturation.service.facade.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CatalogMessage {

    @Autowired
    private BankAccountService bankAccountService;

//    private static int bankAccountThreadIndex = 0;
//    static ThreadFactory bankAccountThreadFactory = (r) -> new Thread(r, "BankAccountC-" + bankAccountThreadIndex++);

    @Async
//    @JmsListener(destination = "newSupplier", containerFactory = "jmsListenerContainerFactory", concurrency = "3")
    public void receiveMessage(Message<String> orderDto) {
        System.out.println("Current Thread in receiveMessage: " + Thread.currentThread());
        System.out.println("Message reçu du topic : " + orderDto);
        String payload = orderDto.getPayload();
        bankAccountService.setDataProviderToBankAccount(payload);

//        ExecutorService bankAccountExecutorService = Executors.newFixedThreadPool(4, bankAccountThreadFactory);
//
//        CompletableFuture.supplyAsync(() -> bankAccountService.setDataToBankAccount(payload), bankAccountExecutorService);

    }

}
