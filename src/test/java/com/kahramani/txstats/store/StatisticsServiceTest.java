package com.kahramani.txstats.store;

import com.kahramani.txstats.mapper.DoubleSummaryStatisticsToStatsResponseMapper;
import com.kahramani.txstats.model.response.StatsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.DoubleSummaryStatistics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private TransactionStore transactionStore;
    @Mock
    private DoubleSummaryStatisticsToStatsResponseMapper responseMapper;

    @Test
    public void should_retrieve_stats_response() {
        final DoubleSummaryStatistics mock = new DoubleSummaryStatistics();
        Mockito.when(transactionStore.findStatsBefore(anyLong())).thenReturn(mock);

        StatsResponse statsResponse = statisticsService.retrieveStatsResponse();

        // assertions
        assertThat(statsResponse).isNull();
        Mockito.verify(responseMapper).apply(mock);
    }
}