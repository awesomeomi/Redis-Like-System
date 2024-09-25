package com.example.redissystem.service;

import com.example.redissystem.model.Transaction;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private Transaction currentTransaction;

    public void begin() {
        if (currentTransaction == null) {
            currentTransaction = new Transaction();
            System.out.println("Transaction Started");
        }else {
            throw new IllegalStateException("A transaction is already in progress");
        }
    }

    public void addCommand(Runnable command) {
        if(currentTransaction != null) {
            currentTransaction.addCommand(command);
        }else {
            throw  new IllegalStateException("No active transaction");
        }
    }

    public void execute() {
        if(currentTransaction != null) {
            currentTransaction.execute();
            currentTransaction = null;
            System.out.println("Transaction executed");
        }else {
            throw new IllegalStateException("No active transaction to execute");
        }
    }

    public void rollback() {
        if(currentTransaction != null) {
            currentTransaction.rollback();
            currentTransaction = null;
            System.out.println("Transaction Rolled Back");
        }else {
            throw new IllegalStateException("No active transaction to rollback");
        }
    }
}
