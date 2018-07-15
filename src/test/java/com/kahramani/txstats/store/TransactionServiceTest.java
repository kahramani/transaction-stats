package com.kahramani.txstats.store;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionStore transactionStore;

    @Test
    public void should_add_transaction() {
        Long timestamp = Instant.now().toEpochMilli();
        Double amount = 12.3D;

        transactionService.add(timestamp, amount);

        Mockito.verify(transactionStore).save(timestamp, 12.3D);
    }
}