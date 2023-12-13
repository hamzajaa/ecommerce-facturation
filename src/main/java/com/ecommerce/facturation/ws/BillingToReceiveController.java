package com.ecommerce.facturation.ws;

import com.ecommerce.facturation.dto.BillingToReceiveDTO;
import com.ecommerce.facturation.service.facade.BillingToReceiveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/billing-to-receive")
public class BillingToReceiveController {
    @Autowired
    public BillingToReceiveService billingToReceiveService;

    @GetMapping("/")
    public ResponseEntity<List<BillingToReceiveDTO>> findAll(){
        return ResponseEntity.ok(billingToReceiveService.getBillingToReceives());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BillingToReceiveDTO> findBillingToReceiveById(@PathVariable Long id){
        return ResponseEntity.ok(billingToReceiveService.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBillingToReceiveById(@PathVariable Long id){
        return ResponseEntity.ok(billingToReceiveService.deleteBillingToReceiveById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<BillingToReceiveDTO> updateBillingToReceive(@Valid @RequestBody BillingToReceiveDTO billingToReceiveDTO, @PathVariable Long id){
        return ResponseEntity.ok(billingToReceiveService.update(billingToReceiveDTO,id));
    }
    @PostMapping("/")
    public ResponseEntity<BillingToReceiveDTO> saveBillingToReceive(@Valid @RequestBody BillingToReceiveDTO billingToReceiveDTO){
        return new ResponseEntity<>(billingToReceiveService.save(billingToReceiveDTO), HttpStatus.CREATED);
    }
}
