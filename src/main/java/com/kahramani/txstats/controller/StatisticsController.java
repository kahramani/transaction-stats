package com.kahramani.txstats.controller;

import com.kahramani.txstats.model.response.StatsResponse;
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

    @GetMapping(value = "/statistics")
    @ResponseStatus(HttpStatus.OK)
    public StatsResponse getStats() {
        ZonedDateTime startTime = ZonedDateTime.now();
        logger.info("Get transaction stats started");
        StatsResponse statsResponse = null; // TODO do your business
        logger.info("Get transaction stats ended. Duration: {} in millis. Response: {}",
                ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now()),
                statsResponse);
        return statsResponse;
    }
}
