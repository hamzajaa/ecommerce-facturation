package com.ecommerce.facturation.service.impl;

import com.ecommerce.facturation.Enum.*;
import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.bean.BankAccountBalance;
import com.ecommerce.facturation.bean.Invoice;
import com.ecommerce.facturation.dao.InvoiceDao;
import com.ecommerce.facturation.dto.*;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;
import com.ecommerce.facturation.mapper.BankAccountBalanceMapper;
import com.ecommerce.facturation.mapper.BankAccountMapper;
import com.ecommerce.facturation.mapper.InvoiceMapper;
import com.ecommerce.facturation.mapper.JsonMapper;
import com.ecommerce.facturation.service.facade.*;
import com.ecommerce.facturation.utils.invoicePdf.GenerateInvoicePdf;
import com.ecommerce.facturation.utils.invoicePdf.SendEmailToClient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private InvoiceMapper invoiceMapper;
    @Autowired
    private GenerateInvoicePdf generateInvoicePdf;
    @Autowired
    private SendEmailToClient sendEmailToClient;
    @Autowired
    private JsonMapper jsonMapper;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BankAccountMapper bankAccountMapper;
    @Autowired
    private BankAccountBalanceService bankAccountBalanceService;
    @Autowired
    private BankAccountBalanceMapper bankAccountBalanceMapper;

    public void setInvoiceMapper(InvoiceMapper invoiceMapper) {
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public List<InvoiceDTO> getInvoices() {
        log.info("Fetching all invoices.");
        return invoiceDao.findAll().stream().map(invoice -> invoiceMapper.fromInvoice(invoice)).collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO findById(Long id) {
        Invoice invoice = invoiceDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found with id: " + id));
        return invoiceMapper.fromInvoice(invoice);
    }

    @Override
//    @Async
    public InvoiceDTO save(InvoiceDTO invoiceDTO) throws BankAccountNotFoundException {
        log.info("Saving a new invoice.");
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice savedInvoice = invoiceDao.save(invoice);
        if (savedInvoice.getInvoiceStatus().equals(InvoiceStatus.PAID)) {
            UserDTO client = new UserDTO(null, savedInvoice.getClientName(),
                    savedInvoice.getClientEmail(),
                    savedInvoice.getClientAddress(),
                    savedInvoice.getClientNumberPhone(),
                    Role.CLIENT);
            UserDTO savedClient = userService.save(client);

            BankAccountDTO bankAccountDTO = new BankAccountDTO(savedInvoice.getInvoiceNumber(),
                    Bank.WAFABANK,
                    savedClient);
            BankAccountDTO savedSenderBankAccountDto = bankAccountService.save(bankAccountDTO);

//            BankAccount foundedRecievedBankAccount = bankAccountService.findByUserRole(Role.ADMIN);
//            BankAccountDTO foundedRecievedBankAccountDto = bankAccountMapper.toDto(foundedRecievedBankAccount);

            BankAccountBalance bankAccountBalance = bankAccountBalanceService.findByBankAccount_UserRole(Role.ADMIN);
            bankAccountBalance.setBalance(bankAccountBalance.getBalance().add(savedInvoice.getTotalPay()));
            bankAccountBalanceService.update(bankAccountBalanceMapper.fromBankAccountBalance(bankAccountBalance));

            BankAccount bankAccountReceived = bankAccountBalance.getBankAccount();
            BankAccountDTO bankAccountReceivedDto = bankAccountMapper.toDto(bankAccountReceived);

            DebitDTO debitDTO = new DebitDTO(
                    savedInvoice.getTotalPay(),
                    savedSenderBankAccountDto,
                    bankAccountReceivedDto,
                    PaymentStatus.Successful,
                    TransactionalType.Sale,
                    invoiceMapper.fromInvoice(savedInvoice)
            );
            transactionService.saveDebit(debitDTO);
        }
        try {
            generateInvoicePdf.generate(savedInvoice);
        } catch (FileNotFoundException e) {
            log.error("Error generating PDF for invoice ID: {}", savedInvoice.getId(), e);
            throw new RuntimeException(e);
        }
//        sendEmailToClient.send(invoice);
        sendEmailToClient.removePdfFile(savedInvoice.getInvoiceNumber());
        return invoiceMapper.fromInvoice(savedInvoice);
    }

    @Override
    @Transactional
    @Async
    public void setDataToInvoice(String payload) throws BankAccountNotFoundException {
        OrderDto orderDto = jsonMapper.convertJsonToObject(payload, OrderDto.class);
        ClientDTO clientDTO = jsonMapper.convertJsonToObject(orderDto.client(), ClientDTO.class);
        List<CommandItemDto> commandItemDtos = jsonMapper.convertJsonToObjects(orderDto.commandItemDtos(), CommandItemDto.class);
        InvoiceDTO invoiceDTO = new InvoiceDTO(
                orderDto.reference(),
                orderDto.orderId(),
                LocalDateTime.now().plusDays(30),
                orderDto.paymentStatus().equals("EN_ATTENTE") ? InvoiceStatus.PENDING : InvoiceStatus.PAID,
                orderDto.paymentMethod().equals("A_LA_LIVRAISON") ? PaymentMethod.ON_DELIVERY : PaymentMethod.ONLINE,
                orderDto.totalPay(),
                clientDTO.fullName(),
                clientDTO.address(),
                clientDTO.email(),
                clientDTO.phoneNumber(),
                commandItemDtos);
        save(invoiceDTO);
    }


    @Override
    public Invoice findByOrderReference(String reference) {
        return invoiceDao.findByOrderReference(reference);
    }

    @Override
    @Transactional
    public void setDataToInvoiceAndUpdate(String payload) throws BankAccountNotFoundException {
        OrderDto orderDto = jsonMapper.convertJsonToObject(payload, OrderDto.class);
        Invoice foundedInvoice = findByOrderId(orderDto.orderId());
        if (orderDto.paymentStatus().equals("PAYE")) {
            foundedInvoice.setInvoiceStatus(InvoiceStatus.PAID);
        }
        update(invoiceMapper.fromInvoice(foundedInvoice));

    }

    @Override
    public Invoice findByOrderId(Long id) {
        return invoiceDao.findByOrderId(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found with orderId: " + id));
    }

    @Override
    @Transactional
    public InvoiceDTO update(InvoiceDTO invoiceDTO) throws BankAccountNotFoundException {
        Invoice invoice = invoiceMapper.fromInvoiceDto(invoiceDTO);
        Invoice updatedInvoice = invoiceDao.save(invoice);

        if (updatedInvoice.getInvoiceStatus().equals(InvoiceStatus.PAID)) {
            UserDTO client = new UserDTO(null, updatedInvoice.getClientName(),
                    updatedInvoice.getClientEmail(),
                    updatedInvoice.getClientAddress(),
                    updatedInvoice.getClientNumberPhone(),
                    Role.CLIENT);
            UserDTO savedClient = userService.save(client);

            BankAccountDTO bankAccountDTO = new BankAccountDTO(updatedInvoice.getInvoiceNumber(),
                    Bank.WAFABANK,
                    savedClient);
            BankAccountDTO savedSenderBankAccountDto = bankAccountService.save(bankAccountDTO);

//            BankAccount foundedRecievedBankAccount = bankAccountService.findByUserRole(Role.ADMIN);
//            BankAccountDTO foundedRecievedBankAccountDto = bankAccountMapper.toDto(foundedRecievedBankAccount);

            BankAccountBalance bankAccountBalance = bankAccountBalanceService.findByBankAccount_UserRole(Role.ADMIN);
            bankAccountBalance.setBalance(bankAccountBalance.getBalance().add(updatedInvoice.getTotalPay()));
            bankAccountBalanceService.update(bankAccountBalanceMapper.fromBankAccountBalance(bankAccountBalance));

            BankAccount bankAccountReceived = bankAccountBalance.getBankAccount();
            BankAccountDTO bankAccountReceivedDto = bankAccountMapper.toDto(bankAccountReceived);

            DebitDTO debitDTO = new DebitDTO(
                    updatedInvoice.getTotalPay(),
                    savedSenderBankAccountDto,
                    bankAccountReceivedDto,
                    PaymentStatus.Successful,
                    TransactionalType.Sale,
                    invoiceMapper.fromInvoice(updatedInvoice)
            );
            transactionService.saveDebit(debitDTO);
        }

        try {
            generateInvoicePdf.generateUpdate(updatedInvoice);
            log.info("PDF Invoice generate successfully. ID {}", updatedInvoice.getId());
        } catch (FileNotFoundException e) {
            log.error("Error generating PDF for invoice ID: {}", updatedInvoice.getId(), e);
            throw new RuntimeException(e);
        }
//        sendEmailToClient.send(invoice);
        sendEmailToClient.removePdfFile(updatedInvoice.getInvoiceNumber());
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


}
