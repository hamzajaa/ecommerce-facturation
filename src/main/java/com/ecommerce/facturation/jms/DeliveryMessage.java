package com.ecommerce.facturation.jms;

import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.service.facade.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Component
public class DeliveryMessage {

    @Autowired
    private BankAccountService bankAccountService;
    private static int bankAccountThreadIndex = 0;
    static ThreadFactory bankAccountThreadFactory = (r) -> new Thread(r, "BankAccountD-" + bankAccountThreadIndex++);


//    @JmsListener(destination = "deliveryFacturation", containerFactory = "jmsListenerContainerFactory", concurrency = "3")
    public void receiveMessage(Message<String> orderDto) {
        System.out.println("Current Thread in receiveMessage: " + Thread.currentThread());
        System.out.println("Message reçu du topic : " + orderDto);
        String payload = orderDto.getPayload();


        ExecutorService bankAccountExecutorService = Executors.newFixedThreadPool(4, bankAccountThreadFactory);

        CompletableFuture.supplyAsync(() -> {
            return bankAccountService.setDataToBankAccount(payload);
        }, bankAccountExecutorService);
    }

}
