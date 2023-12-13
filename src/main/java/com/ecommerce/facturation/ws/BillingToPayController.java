package com.ecommerce.facturation.ws;

import com.ecommerce.facturation.dto.BillingToPayDTO;
import com.ecommerce.facturation.service.facade.BillingToPayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/billing-to-pay")
public class BillingToPayController {
    @Autowired
    public BillingToPayService billingToPayService;

    @GetMapping("/")
    public ResponseEntity<List<BillingToPayDTO>> findAll(){
        return ResponseEntity.ok(billingToPayService.getBillingToPays());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BillingToPayDTO> findBillingToPayById(@PathVariable Long id){
        return ResponseEntity.ok(billingToPayService.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBillingToPayById(@PathVariable Long id){
        return ResponseEntity.ok(billingToPayService.deleteBillingToPayById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BillingToPayDTO> updateBillingToPay(@Valid @RequestBody BillingToPayDTO billingToPayDTO, @PathVariable Long id){
        return ResponseEntity.ok(billingToPayService.update(billingToPayDTO,id));
    }
    @PostMapping("/")
    public ResponseEntity<BillingToPayDTO> saveBillingToPay(@Valid @RequestBody BillingToPayDTO billingToPayDTO){
        return new ResponseEntity<>(billingToPayService.save(billingToPayDTO), HttpStatus.CREATED);
    }
}
