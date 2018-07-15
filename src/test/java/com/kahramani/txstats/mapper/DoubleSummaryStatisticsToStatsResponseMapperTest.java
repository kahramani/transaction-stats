package com.kahramani.txstats.mapper;

import com.kahramani.txstats.model.response.StatsResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.DoubleSummaryStatistics;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleSummaryStatisticsToStatsResponseMapperTest {

    private DoubleSummaryStatisticsToStatsResponseMapper doubleSummaryStatisticsToStatsResponseMapper;

    @Before
    public void setUp() {
        doubleSummaryStatisticsToStatsResponseMapper = new DoubleSummaryStatisticsToStatsResponseMapper();
    }

    @Test
    public void should_map_summary_stats_to_response_when_summary_stats_has_value() {
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
        doubleSummaryStatistics.accept(3d);

        StatsResponse response = doubleSummaryStatisticsToStatsResponseMapper.apply(doubleSummaryStatistics);

        // assertions
        assertThat(response).isNotNull();
        assertThat(response.getAvg()).isEqualTo(3d);
        assertThat(response.getCount()).isEqualTo(1);
        assertThat(response.getMax()).isEqualTo(3d);
        assertThat(response.getMin()).isEqualTo(3d);
        assertThat(response.getSum()).isEqualTo(3d);
    }

    @Test
    public void should_map_summary_stats_to_response_when_only_initial_summary_stats_present() {
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();

        StatsResponse response = doubleSummaryStatisticsToStatsResponseMapper.apply(doubleSummaryStatistics);

        // assertions
        assertThat(response).isNotNull();
        assertThat(response.getAvg()).isEqualTo(0d);
        assertThat(response.getCount()).isEqualTo(0);
        assertThat(response.getMax()).isEqualTo(0d);
        assertThat(response.getMin()).isEqualTo(0d);
        assertThat(response.getSum()).isEqualTo(0d);
    }
}