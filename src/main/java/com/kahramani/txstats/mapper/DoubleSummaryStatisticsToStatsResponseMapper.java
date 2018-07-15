package com.kahramani.txstats.mapper;

import com.kahramani.txstats.model.response.StatsResponse;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.function.Function;

@Service
public class DoubleSummaryStatisticsToStatsResponseMapper implements Function<DoubleSummaryStatistics, StatsResponse> {

    @Override
    public StatsResponse apply(DoubleSummaryStatistics doubleSummaryStatistics) {
        StatsResponse statsResponse = new StatsResponse();
        statsResponse.setMin(doubleSummaryStatistics.getMin() == Double.POSITIVE_INFINITY ? 0d : doubleSummaryStatistics.getMin());
        statsResponse.setMax(doubleSummaryStatistics.getMax() == Double.NEGATIVE_INFINITY ? 0d : doubleSummaryStatistics.getMax());
        statsResponse.setAvg(doubleSummaryStatistics.getAverage());
        statsResponse.setCount((double) doubleSummaryStatistics.getCount());
        statsResponse.setSum(doubleSummaryStatistics.getSum());
        return statsResponse;
    }
}
