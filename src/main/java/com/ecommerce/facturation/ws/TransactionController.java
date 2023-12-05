package com.ecommerce.facturation.ws;


import com.ecommerce.facturation.dto.TransactionDto;
import com.ecommerce.facturation.service.facade.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public ResponseEntity<List<TransactionDto>> findAll() {
        return ResponseEntity.ok(transactionService.findAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.findTransactionById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.deleteTransaction(id));
    }

    @PostMapping("/")
    public ResponseEntity<TransactionDto> save( @RequestBody @Valid TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.saveTransaction(transactionDto), HttpStatus.CREATED);
    }

    @PostMapping("/list/")
    public ResponseEntity<List<TransactionDto>> saveall(@RequestBody @Valid List<TransactionDto> transactionDtos) {
        return new ResponseEntity<>(transactionService.saveTransactions(transactionDtos), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> update(@RequestBody @Valid TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.updateTransaction(transactionDto));
    }

    @DeleteMapping("/")
    public ResponseEntity<Boolean> delete(@RequestBody @Valid TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.deleteTransaction(transactionDto.id()));
    }

    @DeleteMapping("/list/")
    public void delete(@RequestBody @Valid List<TransactionDto> transactionDtos) {
        transactionService.deleteTransactions(transactionDtos);
    }

    @PutMapping("/list/")
    public void update(@RequestBody @Valid List<TransactionDto> list) {
        transactionService.updateTransactions(list);
    }
}
