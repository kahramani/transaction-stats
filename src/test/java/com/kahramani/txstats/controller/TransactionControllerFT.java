package com.kahramani.txstats.controller;

import com.kahramani.txstats.model.request.TransactionRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Functional tests for /transactions endpoint.
 * Checks if everything works right in spring application context and relations between business layers
 * Edge cases covered on unit tests of each services
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TransactionControllerFT {

    private final static String ENDPOINT = "/api/v1/transactions";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Test
    public void should_return_http_201() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(0.0d);
        transactionRequest.setTimestamp(Instant.now().toEpochMilli());

        // call the endpoint
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + ENDPOINT);
        ResponseEntity<Void> response = testRestTemplate.exchange(builder.build().encode().toUri(), HttpMethod.POST, new HttpEntity<>(transactionRequest), Void.class);

        // assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}