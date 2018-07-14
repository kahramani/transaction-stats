package com.kahramani.txstats.controller;

import com.kahramani.txstats.model.request.TransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping(value = "/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransactionRequest transactionRequest) {
        ZonedDateTime startTime = ZonedDateTime.now();
        logger.info("Create transaction started with request: {}", transactionRequest); // request contains no sensitive data so logging shouldn't be a problem
        // TODO validate the request
        // TODO do your business
        logger.info("Create transaction ended. Duration: {} in millis.", ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now()));
    }
}
