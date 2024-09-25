package com.example.redissystem.controller;

import com.example.redissystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/begin")
    public ResponseEntity<String> beginTransaction() {
        transactionService.begin();
        return ResponseEntity.ok("Transaction started.");
    }

    @PostMapping("/commit")
    public ResponseEntity<String> commitTransaction() {
        transactionService.execute();
        return ResponseEntity.ok("Transaction committed.");
    }

    @PostMapping("/rollback")
    public ResponseEntity<String> rollbackTransaction() {
        transactionService.rollback();
        return ResponseEntity.ok("Transaction rolled back.");
    }
}
