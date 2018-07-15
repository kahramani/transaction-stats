package com.kahramani.txstats.controller;

import com.kahramani.txstats.model.response.StatsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Functional tests for /statistics endpoint
 * Checks if everything works right in spring application context and relations between business layers
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StatisticsControllerFT {

    private final static String ENDPOINT = "/api/v1/statistics";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Test
    public void should_get_statistic_with_null_body() {
        // call the endpoint
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + serverPort + ENDPOINT);
        ResponseEntity<StatsResponse> response = testRestTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, null, StatsResponse.class);

        // assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        StatsResponse statsResponse = response.getBody();
        assertThat(statsResponse).isNotNull();
    }
}