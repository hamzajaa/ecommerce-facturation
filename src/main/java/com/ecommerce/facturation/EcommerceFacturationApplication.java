package com.ecommerce.facturation;

import com.ecommerce.facturation.bean.Credit;
import com.ecommerce.facturation.dto.OrderDto;
import com.ecommerce.facturation.jms.MessageConsumer;
import com.ecommerce.facturation.ws.InvoiceController;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@SpringBootApplication
public class EcommerceFacturationApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceFacturationApplication.class, args);
    }

//    @Autowired
//    private JsonMapper jsonMapper;

    @Override
    public void run(String... args) throws Exception {

        String jsonMessage = """
                {
                  "reference": "your_reference_value",
                  "totalPaye": 123.45,
                  "dateCommande": "2023-01-01T12:34:56",
                  "client":{
                  "fullName":"John Doe",
                  "email":"john.doe@example.com",
                  "address":"123 Main St, Cityville",
                  "phoneNumber":"0652358741"
                  },
                  "commandeItemDtos": [
                     { "id":1,"quantity":2,"prix":49.99, "produit":{"Product_id":8,"nomProduit":"p8","prixProduit":55.99}},
                     { "id":2,"quantity":9,"prix":66.99, "produit":{"Product_id":6,"nomProduit":"p6","prixProduit":22.33}},
                     { "id":3,"quantity":58,"prix":409.99, "produit":{"Product_id":1,"nomProduit":"p1","prixProduit":102.33}}
                     ]
                     }""";

        Message<String> message = new Message<>() {
            @Override
            public String getPayload() {
                return jsonMessage;
            }

            @Override
            public MessageHeaders getHeaders() {
                return null;
            }
        };

        messageConsumer.receiveMessage(message);
        System.out.println(" ");
    }

    @Autowired
    private MessageConsumer messageConsumer;
}