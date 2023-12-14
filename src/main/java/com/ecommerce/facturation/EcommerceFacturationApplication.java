package com.ecommerce.facturation;

import com.ecommerce.facturation.Enum.Bank;
import com.ecommerce.facturation.Enum.Role;
import com.ecommerce.facturation.dto.BankAccountBalanceDTO;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.dto.UserDTO;
import com.ecommerce.facturation.jms.MessageConsumer;
import com.ecommerce.facturation.service.facade.BankAccountBalanceService;
import com.ecommerce.facturation.service.facade.BankAccountService;
import com.ecommerce.facturation.service.facade.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
@EnableJpaAuditing
public class EcommerceFacturationApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceFacturationApplication.class, args);
    }

//    @Autowired
//    private JsonMapper jsonMapper;

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountBalanceService bankAccountBalanceService;


    @Override
    public void run(String... args) throws Exception {

        UserDTO savedUser = null;
        UserDTO userDTO = new UserDTO(null, "UIR", "uirshop@gmail.com", "Technopolis", "+2120662359876", Role.ADMIN);
        if (userService.findByEmail(userDTO.email()) == null) {
            savedUser = userService.save(userDTO);
            BankAccountDTO bankAccountDTO = new BankAccountDTO("rib1", Bank.WAFABANK, savedUser);
            CompletableFuture<BankAccountDTO> savedBankAccount = bankAccountService.save(bankAccountDTO);
            BankAccountBalanceDTO bankAccountBalanceDTO = new BankAccountBalanceDTO(savedBankAccount.join(), BigDecimal.ZERO);
            bankAccountBalanceService.save(bankAccountBalanceDTO);
        }
    }

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

    String json = "{\n" +
            "  \"id\": 2,\n" +
            "  \"reference\": \"cmd-100\",\n" +
            "  \"totalPaye\": 123.45,\n" +
            "  \"dateCommande\": [2023, 11, 29, 22, 14, 19, 934827000],\n" +
            "  \"client\": \"{\\\"fullName\\\":\\\"John Doe\\\",\\\"email\\\":\\\"hamzajaa2022@gmail.com\\\",\\\"address\\\":\\\"123 Main St, Cityville\\\",\\\"phoneNumber\\\":\\\"+1234567890\\\"}\",\n" +
            "  \"commandeItemDtos\": \"[" +
            "{\\\"id\\\":1,\\\"quantity\\\":5,\\\"prix\\\":19.99,\\\"produit\\\":\\\"{\\\\\\\"id\\\\\\\":1,\\\\\\\"reference\\\\\\\":\\\\\\\"REF123\\\\\\\",\\\\\\\"prixProduit\\\\\\\":\\\\\\\"10\\\\\\\",\\\\\\\"libelle\\\\\\\":\\\\\\\"ProductA\\\\\\\",\\\\\\\"categorieProduitDto\\\\\\\":{\\\\\\\"id\\\\\\\":101,\\\\\\\"libele\\\\\\\":\\\\\\\"CategoryA\\\\\\\"}}\\\"}," +
            "{\\\"id\\\":2,\\\"quantity\\\":3,\\\"prix\\\":29.99,\\\"produit\\\":\\\"{\\\\\\\"id\\\\\\\":2,\\\\\\\"reference\\\\\\\":\\\\\\\"REF456\\\\\\\",\\\\\\\"prixProduit\\\\\\\":\\\\\\\"20.55\\\\\\\",\\\\\\\"libelle\\\\\\\":\\\\\\\"ProductB\\\\\\\",\\\\\\\"categorieProduitDto\\\\\\\":{\\\\\\\"id\\\\\\\":102,\\\\\\\"libele\\\\\\\":\\\\\\\"CategoryB\\\\\\\"}}\\\"}," +
            "{\\\"id\\\":3,\\\"quantity\\\":1,\\\"prix\\\":9.99,\\\"produit\\\":\\\"{\\\\\\\"id\\\\\\\":3,\\\\\\\"reference\\\\\\\":\\\\\\\"REF789\\\\\\\",\\\\\\\"prixProduit\\\\\\\":\\\\\\\"5.99\\\\\\\",\\\\\\\"libelle\\\\\\\":\\\\\\\"ProductC\\\\\\\",\\\\\\\"categorieProduitDto\\\\\\\":{\\\\\\\"id\\\\\\\":103,\\\\\\\"libele\\\\\\\":\\\\\\\"CategoryC\\\\\\\"}}\\\"}]\"\n" +
            "}";

    Message<String> message = new Message<>() {
        @Override
        public String getPayload() {
            return json;
        }

        @Override
        public MessageHeaders getHeaders() {
            return null;
        }

    };


    @Autowired
    private MessageConsumer messageConsumer;
}