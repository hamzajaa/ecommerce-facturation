package com.ecommerce.facturation.ws;

import com.ecommerce.facturation.dto.BankAccountBalanceDTO;
import com.ecommerce.facturation.exception.BankAccountBalanceNotFoundException;
import com.ecommerce.facturation.exception.BankAccountNotFoundException;
import com.ecommerce.facturation.service.impl.BankAccountBalanceServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/BankAccountBalance")
public class BankAccountBalanceController {
    public BankAccountBalanceServiceImpl bankAccountBalanceService;


    @GetMapping("/")
    public ResponseEntity<List<BankAccountBalanceDTO>> findAll(){
        return ResponseEntity.ok(bankAccountBalanceService.getBankAccountBalances());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountBalanceDTO> findBankAccountBalanceById(@PathVariable Long id) throws BankAccountBalanceNotFoundException {
        return ResponseEntity.ok(bankAccountBalanceService.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBankAccountBalanceById(@PathVariable Long id) throws BankAccountBalanceNotFoundException {
        return ResponseEntity.ok(bankAccountBalanceService.deleteBankAccountBalanceById(id));
    }
    @PutMapping("/")
    public ResponseEntity<BankAccountBalanceDTO> updateBankAccountBalance(@Valid @RequestBody BankAccountBalanceDTO bankAccountBalanceDTO) throws BankAccountNotFoundException {
        return ResponseEntity.ok(bankAccountBalanceService.update(bankAccountBalanceDTO));
    }
    @PostMapping("/")
    public ResponseEntity<BankAccountBalanceDTO> saveBankAccountBalance(@Valid @RequestBody BankAccountBalanceDTO bankAccountBalanceDTO){
        return new ResponseEntity<>(bankAccountBalanceService.save(bankAccountBalanceDTO), HttpStatus.CREATED);
    }
}
