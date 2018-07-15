package com.kahramani.txstats.store;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeUnit;

/**
 * Spring bean used as data-store. This store holds data in whole lifecycle of the application.
 */
public class TransactionStore {

    private static final long STATS_REFRESH_SCOPE_IN_SECONDS = TimeUnit.MINUTES.toSeconds(1);

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

    /**
     * Specifies the bucket to put given amount. Method handles writing process in thread-safe mode.
     */
    void save(Long timestamp, Double amount) {
        long bucket = ChronoUnit.SECONDS.between(mapCreationDateTime, LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault()));
        putVal(bucket, amount);
    }

    /**
     * All stats combined here.
     * Stats size can be 60 at most and everyone represents statistics of each second.
     */
    DoubleSummaryStatistics findStats() {
        DoubleSummaryStatistics result = new DoubleSummaryStatistics();
        retrieveSnapshotOfValidRange().forEach(result::combine);
        return result;
    }

    /**
     * Method will return 60 object at most. This leads us to achieve O(1) complexity.
     * Each object holds stats for specific range
     */
    private Collection<DoubleSummaryStatistics> retrieveSnapshotOfValidRange() {
        long duration = ChronoUnit.SECONDS.between(mapCreationDateTime, LocalDateTime.now());
        if (duration < STATS_REFRESH_SCOPE_IN_SECONDS) {
            return concurrentNavigableMap.values();
        }
        return concurrentNavigableMap.tailMap(duration - STATS_REFRESH_SCOPE_IN_SECONDS).values();
    }

    private synchronized void putVal(long key, Double value) {
        DoubleSummaryStatistics bucketStats = concurrentNavigableMap.getOrDefault(key, new DoubleSummaryStatistics());
        bucketStats.accept(value);
        concurrentNavigableMap.put(key, bucketStats);
    }
}