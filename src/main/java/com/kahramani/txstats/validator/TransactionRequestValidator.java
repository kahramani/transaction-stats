package com.kahramani.txstats.validator;

import com.kahramani.txstats.exception.RequestValidationException;
import com.kahramani.txstats.exception.UnacceptableRequestTimestampValidationException;
import com.kahramani.txstats.model.request.TransactionRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Accept only valid requests for your business, others will be responded with a clear message
 */
@Service
public class TransactionRequestValidator {

    private static final long TIMESTAMP_ACCEPTANCE_THRESHOLD_IN_SECONDS = TimeUnit.MINUTES.toSeconds(1);

    public void validate(TransactionRequest transactionRequest) {
        validateIfPresentOrThrow(transactionRequest, () -> new RequestValidationException("validation.request.not.present"));
        validateIfPresentOrThrow(transactionRequest.getAmount(), () -> new RequestValidationException("validation.request.field.not.present", "amount"));
        validateIfPresentOrThrow(transactionRequest.getTimestamp(), () -> new RequestValidationException("validation.request.field.not.present", "timestamp"));
        validateTimestamp(transactionRequest.getTimestamp());
    }

    private void validateIfPresentOrThrow(Object o, Supplier<RequestValidationException> exceptionSupplier) {
        Optional.ofNullable(o)
                .orElseThrow(exceptionSupplier);
    }

    private void validateTimestamp(Long timestamp) {
        LocalDateTime timestampLocal = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), TimeZone.getDefault().toZoneId());
        long durationInSeconds = Duration.between(LocalDateTime.now(), timestampLocal).getSeconds();
        if (Math.abs(durationInSeconds) > TIMESTAMP_ACCEPTANCE_THRESHOLD_IN_SECONDS) { // older 60 secs or future timestamps unacceptable
            throw new UnacceptableRequestTimestampValidationException();
        }
    }
}