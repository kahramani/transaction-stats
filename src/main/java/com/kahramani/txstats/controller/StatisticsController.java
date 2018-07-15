package com.kahramani.txstats.controller;

import com.kahramani.txstats.model.response.StatsResponse;
import com.kahramani.txstats.store.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/v1")
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "/statistics")
    @ResponseStatus(HttpStatus.OK)
    public StatsResponse getStats() {
        ZonedDateTime startTime = ZonedDateTime.now();
        logger.info("Get transaction stats started");
        StatsResponse statsResponse = statisticsService.retrieveStatsResponse();
        logger.info("Get transaction stats ended. Duration: {} in millis. Response: {}",
                ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now()),
                statsResponse);
        return statsResponse;
    }

    @GetMapping(value = "/refresh")
    @ResponseStatus(HttpStatus.OK)
    public void refresh() {
        ZonedDateTime startTime = ZonedDateTime.now();
        logger.info("Refresh transaction stats started");
        statisticsService.refreshStats();
        logger.info("Refresh transaction stats ended. Duration: {} in millis.", ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now()));
    }
}
