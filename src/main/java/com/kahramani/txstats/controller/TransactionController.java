package com.kahramani.txstats.controller;

import com.kahramani.txstats.model.request.TransactionRequest;
import com.kahramani.txstats.store.TransactionService;
import com.kahramani.txstats.validator.TransactionRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Transactions endpoint to accept transaction requests
 */
@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionRequestValidator transactionRequestValidator;
    private final TransactionService transactionService;

    public TransactionController(TransactionRequestValidator transactionRequestValidator,
                                 TransactionService transactionService) {
        this.transactionRequestValidator = transactionRequestValidator;
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTransaction(@RequestBody TransactionRequest transactionRequest) {
        ZonedDateTime startTime = ZonedDateTime.now();
        logger.info("Create transaction started with request: {}", transactionRequest); // request contains no sensitive data so logging shouldn't be a problem
        transactionRequestValidator.validate(transactionRequest);
        transactionService.add(transactionRequest.getTimestamp(), transactionRequest.getAmount());
        logger.info("Create transaction ended. Duration: {} in millis.", ChronoUnit.MILLIS.between(startTime, ZonedDateTime.now()));
    }
}
