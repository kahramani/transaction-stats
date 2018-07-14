package com.kahramani.txstats.validator;

import com.kahramani.txstats.exception.RequestValidationException;
import com.kahramani.txstats.exception.UnacceptableRequestTimestampValidationException;
import com.kahramani.txstats.model.request.TransactionRequest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class TransactionRequestValidatorTest {

    private TransactionRequestValidator transactionRequestValidator;

    @Before
    public void setUp() {
        transactionRequestValidator = new TransactionRequestValidator();
    }

    @Test
    public void should_validate_when_request_is_valid() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new Double("12.3"));
        transactionRequest.setTimestamp(Instant.now().toEpochMilli());

        Throwable throwable = Assertions.catchThrowable(() -> transactionRequestValidator.validate(transactionRequest));

        assertThat(throwable).isNull();
    }

    @Test
    public void should_validate_when_request_timestamp_is_59_secs_ago() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new Double("12.3"));
        long nearlyOneMinuteAgo = Instant.now().minusMillis(TimeUnit.SECONDS.toMillis(59)).toEpochMilli();
        transactionRequest.setTimestamp(nearlyOneMinuteAgo);

        Throwable throwable = Assertions.catchThrowable(() -> transactionRequestValidator.validate(transactionRequest));

        assertThat(throwable).isNull();
    }

    @Test
    public void should_throw_RequestValidationException_when_request_is_null() {
        Throwable throwable = Assertions.catchThrowable(() -> transactionRequestValidator.validate(null));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.not.present");
    }

    @Test
    public void should_throw_RequestValidationException_when_amount_is_null() {
        TransactionRequest transactionRequest = new TransactionRequest();

        Throwable throwable = Assertions.catchThrowable(() -> transactionRequestValidator.validate(transactionRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.not.present");
    }

    @Test
    public void should_throw_RequestValidationException_when_timestamp_is_null() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new Double("12.3"));

        Throwable throwable = Assertions.catchThrowable(() -> transactionRequestValidator.validate(transactionRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("validation.request.field.not.present");
    }

    @Test
    public void should_throw_RequestValidationException_when_timestamp_is_2_minutes_ago() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new Double("12.3"));
        long twoMinutesAgo = Instant.now().minusMillis(TimeUnit.MINUTES.toMillis(2)).toEpochMilli();
        transactionRequest.setTimestamp(twoMinutesAgo);

        Throwable throwable = Assertions.catchThrowable(() -> transactionRequestValidator.validate(transactionRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(UnacceptableRequestTimestampValidationException.class);
    }

    @Test
    public void should_throw_RequestValidationException_when_timestamp_is_2_minutes_later() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(new Double("12.3"));
        long twoMinutesLater = Instant.now().plusMillis(TimeUnit.MINUTES.toMillis(2)).toEpochMilli();
        transactionRequest.setTimestamp(twoMinutesLater);

        Throwable throwable = Assertions.catchThrowable(() -> transactionRequestValidator.validate(transactionRequest));

        assertThat(throwable)
                .isNotNull()
                .isInstanceOf(UnacceptableRequestTimestampValidationException.class);
    }
}