package com.ecommerce.facturation.ws;

import com.ecommerce.facturation.bean.Credit;
import com.ecommerce.facturation.dto.CreditDTO;
import com.ecommerce.facturation.dto.DebitDTO;
import com.ecommerce.facturation.dto.TransactionDTO;
import com.ecommerce.facturation.service.facade.TransactionService;
import com.ecommerce.facturation.validator.ValidLongId;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @GetMapping("/")
    public ResponseEntity<List<TransactionDTO>> allTransactions(){
        return ResponseEntity.ok(transactionService.getTransactions());
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable @ValidLongId Long id){
        return ResponseEntity.ok(transactionService.findById(id));
    }
    @PostMapping("/credit")
    public ResponseEntity<CreditDTO> saveCredit(@RequestBody @Valid CreditDTO creditDTO){
        return new ResponseEntity<>(transactionService.saveCredit(creditDTO), HttpStatus.CREATED);
    }
    @PostMapping("/debit")
    public ResponseEntity<DebitDTO> saveDebit(@RequestBody @Valid DebitDTO debitDTO){
        return new ResponseEntity<>(transactionService.saveDebit(debitDTO), HttpStatus.CREATED);
    }

    @PutMapping("/credit/{id}")
    public ResponseEntity<CreditDTO> updateCredit(@PathVariable @ValidLongId Long id,@RequestBody @Valid CreditDTO creditDTO){
        return ResponseEntity.ok(transactionService.updateCredit(creditDTO,id));
    }
    @PutMapping("/debit/{id}")
    public ResponseEntity<DebitDTO> updateCredit(@PathVariable @ValidLongId Long id,@RequestBody @Valid DebitDTO debitDTO){
        return ResponseEntity.ok(transactionService.updateDebit(debitDTO,id));
    }

    @DeleteMapping("/")
    public ResponseEntity<Boolean> deleteTransaction(@PathVariable @ValidLongId Long id){
        return ResponseEntity.ok(transactionService.deleteTransactionById(id));
    }
}
