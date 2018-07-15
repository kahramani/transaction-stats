package com.kahramani.txstats.exception;

import com.kahramani.txstats.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to manage all rest exceptions on one hand.
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
    private static final String ERROR_MESSAGE_SPLITTER = ";";

    private final ErrorMessageLocalizationService errorMessageLocalizationService;

    public RestControllerExceptionHandler(ErrorMessageLocalizationService errorMessageLocalizationService) {
        this.errorMessageLocalizationService = errorMessageLocalizationService;
    }

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ErrorResponse> handleRequestValidationException(RequestValidationException e) {
        logger.warn("A request validation exception occurred.", e); // client-based error so log level can be warn
        ErrorResponse response = createErrorResponse(e.getMessage(), e.getArguments());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(UnacceptableRequestTimestampValidationException.class)
    public ResponseEntity<Void> handleUnacceptableRequestTimestampValidationException(UnacceptableRequestTimestampValidationException e) {
        logger.warn("An unacceptable request timestamp validation exception occurred.", e); // client-based error so log level can be warn
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.warn("A http request method not supported exception occurred", e);
        ErrorResponse response = createErrorResponse("validation.request.http.verb.not.supported");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.warn("A http message not readable exception occurred", e);
        ErrorResponse response = createErrorResponse("validation.request.http.message.not.readable");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error("An error occurred.", e); // server error so log level must be error
        ErrorResponse response = createErrorResponse("system.error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ErrorResponse createErrorResponse(String messageKey, Object... args) {
        String message = errorMessageLocalizationService.getLocaleMessage(messageKey, args);
        String[] errorPair = message.split(ERROR_MESSAGE_SPLITTER);
        return new ErrorResponse(errorPair[0], errorPair[1], errorMessageLocalizationService.getLocale().getLanguage());
    }
}