package com.kahramani.txstats.store;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionStore transactionStore;

    public TransactionService(TransactionStore transactionStore) {
        this.transactionStore = transactionStore;
    }

    public void add(Long timestamp, Double amount) {
        transactionStore.save(timestamp, amount);
    }
}