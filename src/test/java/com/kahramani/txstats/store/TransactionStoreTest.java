package com.kahramani.txstats.store;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionStoreTest {

    private TransactionStore transactionStore;

    @Before
    public void setUp() {
        transactionStore = TransactionStore.getInstance();
    }

    @Test
    public void should_save_and_give_stats_concurrently() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        int numberOfThreads = 3;

        Runnable producer = () -> IntStream.rangeClosed(1, 2000)
                .forEach(index -> transactionStore.save(Instant.now().minusMillis(index).toEpochMilli(), (double) index));

        IntStream.rangeClosed(0, numberOfThreads)
                .forEach(index -> executorService.execute(producer));

        executorService.awaitTermination(3, TimeUnit.SECONDS);

        DoubleSummaryStatistics stats = transactionStore.findStats();

        // assertions
        assertThat(stats).isNotNull();
        assertThat(stats.getMax()).isEqualTo(2000d);
        assertThat(stats.getMin()).isEqualTo(1d);
        assertThat(stats.getSum()).isEqualTo(8004000d);
        assertThat(stats.getCount()).isEqualTo(8000);
        assertThat(stats.getAverage()).isEqualTo(1000.5d);
    }
}