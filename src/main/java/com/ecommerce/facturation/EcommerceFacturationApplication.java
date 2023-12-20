package com.ecommerce.facturation;

import com.ecommerce.facturation.Enum.Bank;
import com.ecommerce.facturation.Enum.Role;
import com.ecommerce.facturation.dto.BankAccountBalanceDTO;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.dto.UserDTO;
import com.ecommerce.facturation.jms.MessageConsumer;
import com.ecommerce.facturation.service.facade.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.math.BigDecimal;

@SpringBootApplication
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
    @Autowired
    private BillingToPayService billingToPayService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BillingToReceiveService billingToReceiveService;


    @Override
    public void run(String... args) throws Exception {


        if (userService.findByEmail("uirshop@gmail.com") == null) {
            UserDTO userDTO = new UserDTO(null, "UIR", "uirshop@gmail.com", "Technopolis", "+2120662359876", Role.ADMIN);
            UserDTO savedUser = userService.save(userDTO);
            BankAccountDTO bankAccountDTO = new BankAccountDTO("rib1", Bank.WAFABANK, savedUser);
            BankAccountDTO savedBankAccountUir = bankAccountService.save(bankAccountDTO);
            BankAccountBalanceDTO bankAccountBalanceDTO = new BankAccountBalanceDTO(savedBankAccountUir, BigDecimal.ZERO);
            bankAccountBalanceService.save(bankAccountBalanceDTO);
        }


//        UserDTO userDTO1 = new UserDTO(null,"WALID","zaanoun3@gmail.com","ddsqdsqdsqdd","+2121234567890",Role.CLIENT);
//        UserDTO savedUser1 = userService.save(userDTO1);
//        BankAccountDTO bankAccountDTO = new BankAccountDTO("12345678212",Bank.CIHBANK,savedUser1);
//        BankAccountDTO savedBankAccount = bankAccountService.save(bankAccountDTO);
//
//        BillingToPayDTO billingToPayDTO = new BillingToPayDTO(new BigDecimal(100), PaymentStatus.Successful,savedUser1, TransactionalType.Sale);
//        BillingToPayDTO billingToPayDTO2 = new BillingToPayDTO(new BigDecimal(200), PaymentStatus.Successful,savedUser1, TransactionalType.Sale);
//        BillingToPayDTO savedBillingToPay = billingToPayService.save(billingToPayDTO);
//        BillingToPayDTO savedBillingToPay1 = billingToPayService.save(billingToPayDTO2);

        ////////////
//        CreditDTO creditDTO= new CreditDTO();
//
//
//        creditDTO.setAmount(new BigDecimal(300));
//        creditDTO.setSender(savedBankAccountUir);
//        creditDTO.setReceiver(savedBankAccount);
//        creditDTO.setPaymentStatus(PaymentStatus.Successful);
//        creditDTO.setTransactionalType(TransactionalType.Sale);
//        creditDTO.setInvoice(null);
//
//        CreditDTO saveCredit = transactionService.saveCredit(creditDTO);
//
//        List<CreditBillingToPayDTO> creditBillingToPayDTOS = new ArrayList<>();
//
//        CreditBillingToPayDTO creditBillingToPayDTO = new CreditBillingToPayDTO(null,saveCredit,savedBillingToPay);
//        creditBillingToPayDTOS.add(creditBillingToPayDTO);
//
//        CreditBillingToPayDTO creditBillingToPayDTO1 = new CreditBillingToPayDTO(null,saveCredit,savedBillingToPay1);
//        creditBillingToPayDTOS.add(creditBillingToPayDTO1);
//
//        saveCredit.setCreditBillingToPays(creditBillingToPayDTOS);
//        //CreditDTO savedCredit1 = transactionService.saveCredit(saveCredit);
//        // TODO create Service for Credit Billing to Pay
//        //////
//
//        BillingToReceiveDTO billingToReceiveDTO = new BillingToReceiveDTO(new BigDecimal(100),PaymentStatus.Successful,savedUser1,TransactionalType.Sale);
//        BillingToReceiveDTO billingToReceiveDTO1 = new BillingToReceiveDTO(new BigDecimal(200), PaymentStatus.Successful,savedUser1,TransactionalType.Sale);
//        BillingToReceiveDTO savedBillingToReceiveDTO = billingToReceiveService.save(billingToReceiveDTO);
//        BillingToReceiveDTO savedBillingToReceiveDTO2 = billingToReceiveService.save(billingToReceiveDTO1);
//
//        DebitDTO debitDTO = new DebitDTO();
//        debitDTO.setAmount(new BigDecimal(300));
//        debitDTO.setInvoice(null);
//        debitDTO.setSender(savedBankAccount);
//        debitDTO.setReceiver(savedBankAccountUir);
//        debitDTO.setPaymentStatus(PaymentStatus.Successful);
//        debitDTO.setTransactionalType(TransactionalType.Sale);
//
//        DebitDTO savedDebitDTO = transactionService.saveDebit(debitDTO);
//
//        List<DebitBillingToReceiveDTO> debitBillingToReceiveDTOS=new ArrayList<>();
//        DebitBillingToReceiveDTO debitBillingToReceiveDTO = new DebitBillingToReceiveDTO(null,savedDebitDTO,savedBillingToReceiveDTO);
//        DebitBillingToReceiveDTO debitBillingToReceiveDTO1 = new DebitBillingToReceiveDTO(null,savedDebitDTO,savedBillingToReceiveDTO2);
//        debitBillingToReceiveDTOS.add(debitBillingToReceiveDTO);
//        debitBillingToReceiveDTOS.add(debitBillingToReceiveDTO1);
//
//        savedDebitDTO.setDebitBillingToReceiveDTOS(debitBillingToReceiveDTOS);
//
//        //DebitDTO saveDebitDTO1 = transactionService.saveDebit(savedDebitDTO);
//
//        // TODO create Service for Credit Billing to Pay


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