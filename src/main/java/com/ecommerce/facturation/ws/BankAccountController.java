package com.ecommerce.facturation.ws;

import com.ecommerce.facturation.bean.BankAccount;
import com.ecommerce.facturation.dto.BankAccountDTO;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;
import com.ecommerce.facturation.service.impl.BankAccountServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/BankAccounts")
public class BankAccountController {
    @Autowired
    public BankAccountServiceImpl bankAccountService;

    @GetMapping("/")
    public ResponseEntity<List<BankAccountDTO>> findAll() {
        return ResponseEntity.ok(bankAccountService.getBankAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> findBankAccountById(@PathVariable Long id) throws BankAccountNotFoundException {
        return ResponseEntity.ok(bankAccountService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBankAccountById(@PathVariable Long id) throws BankAccountNotFoundException {
        return ResponseEntity.ok(bankAccountService.deleteBankAccountById(id));
    }

    @PutMapping("/")
    public ResponseEntity<BankAccountDTO> updateBankAccount(@Valid @RequestBody BankAccountDTO bankAccountDTO) {
        return ResponseEntity.ok(bankAccountService.update(bankAccountDTO));
    }

    @PostMapping("/")
    public ResponseEntity<CompletableFuture<BankAccountDTO>> saveBankAccount(@Valid @RequestBody BankAccountDTO bankAccountDTO) {
        return new ResponseEntity<>(bankAccountService.save(bankAccountDTO), HttpStatus.CREATED);
    }
}
