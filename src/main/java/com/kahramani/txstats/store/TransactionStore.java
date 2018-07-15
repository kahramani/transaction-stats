package com.kahramani.txstats.store;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class TransactionStore {

    private static TransactionStore INSTANCE;
    private LocalDateTime mapCreationDateTime;
    private ConcurrentNavigableMap<Long, DoubleSummaryStatistics> concurrentNavigableMap;

    private TransactionStore() {
    }

    public static TransactionStore getInstance() {
        return Optional.ofNullable(INSTANCE)
                .orElseGet(() -> {
                    INSTANCE = new TransactionStore();
                    INSTANCE.mapCreationDateTime = LocalDateTime.now();
                    INSTANCE.concurrentNavigableMap = new ConcurrentSkipListMap<>(Comparator.comparingLong(i -> i));
                    return INSTANCE;
                });
    }

    void save(Long timestamp, Double amount) {
        long bucket = ChronoUnit.SECONDS.between(mapCreationDateTime, LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
        putVal(bucket, amount);
    }

    DoubleSummaryStatistics findStatsBefore(long beforeTimestamp) {
        long duration = ChronoUnit.SECONDS.between(mapCreationDateTime, LocalDateTime.ofInstant(Instant.ofEpochMilli(beforeTimestamp), ZoneId.systemDefault()));
        DoubleSummaryStatistics result = new DoubleSummaryStatistics();
        if (duration < 60) {
            concurrentNavigableMap.values().forEach(result::combine);
        } else {
            concurrentNavigableMap.tailMap(duration - 60).values().forEach(result::combine);
        }
        return result;
    }

    void flushStore() {
        INSTANCE = null;
    }

    private synchronized void putVal(long key, Double value) {
        DoubleSummaryStatistics bucketStats = concurrentNavigableMap.getOrDefault(key, new DoubleSummaryStatistics());
        bucketStats.accept(value);
        concurrentNavigableMap.put(key, bucketStats);
    }
}
