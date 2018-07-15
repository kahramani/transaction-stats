package com.kahramani.txstats.store;

import com.kahramani.txstats.mapper.DoubleSummaryStatisticsToStatsResponseMapper;
import com.kahramani.txstats.model.response.StatsResponse;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;

@Service
public class StatisticsService {

    private final TransactionStore transactionStore;
    private final DoubleSummaryStatisticsToStatsResponseMapper responseMapper;

    public StatisticsService(TransactionStore transactionStore, DoubleSummaryStatisticsToStatsResponseMapper responseMapper) {
        this.transactionStore = transactionStore;
        this.responseMapper = responseMapper;
    }

    public StatsResponse retrieveStatsResponse() {
        DoubleSummaryStatistics stats = transactionStore.findStats();
        return responseMapper.apply(stats);
    }
}