package com.project.paymentservice.controller;

import com.project.paymentservice.DAO.PaymentVO;
import com.project.paymentservice.model.TransactionDetailsEntity;
import com.project.paymentservice.service.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentServiceImpl paymentService;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value="/transaction",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentVO> makePayment(@RequestBody PaymentVO paymentVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.makePayment(paymentVO));
    }

    @GetMapping(value="/transaction/{transactionId}")
    public ResponseEntity<Optional<TransactionDetailsEntity>> findTransactionById(@PathVariable int transactionId){
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.findTransactionById(transactionId));
    }
}
