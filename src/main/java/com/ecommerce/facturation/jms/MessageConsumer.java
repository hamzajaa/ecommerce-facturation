package com.ecommerce.facturation.jms;

import com.ecommerce.facturation.service.facade.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @Autowired
    private InvoiceService invoiceService;

    @JmsListener(destination = "commandeBilling2", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(Message<String> orderDto) {
        System.out.println("Message reçu du topic : " + orderDto);
        String payload = orderDto.getPayload();
        invoiceService.setDataToInvoice(payload);
    }


}
