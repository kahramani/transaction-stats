package com.kahramani.txstats.exception;

import com.kahramani.txstats.model.response.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RestControllerExceptionHandlerTest {

    @InjectMocks
    private RestControllerExceptionHandler restControllerExceptionHandler;

    @Mock
    private ErrorMessageLocalizationService errorMessageLocalizationService;

    @Before
    public void setUp() {
        Mockito.when(errorMessageLocalizationService.getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    public void should_return_http_422_with_a_body_when_requestValidationException_occurs() {
        RequestValidationException exception = new RequestValidationException("message.key", "test");
        Mockito.when(errorMessageLocalizationService.getLocaleMessage("message.key", "test")).thenReturn("999;test is mandatory");

        ResponseEntity<ErrorResponse> response = restControllerExceptionHandler.handleRequestValidationException(exception);

        // assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        ErrorResponse errorResponse = response.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.getLocale()).isEqualTo("en");
        assertThat(errorResponse.getErrorCode()).isEqualTo("999");
        assertThat(errorResponse.getErrorMessage()).isEqualTo("test is mandatory");
    }

    @Test
    public void should_return_http_204_without_a_body_when_unacceptableRequestTimestampValidationException_occurs() {
        UnacceptableRequestTimestampValidationException exception = new UnacceptableRequestTimestampValidationException();

        ResponseEntity<Void> response = restControllerExceptionHandler.handleUnacceptableRequestTimestampValidationException(exception);

        // assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void should_return_http_500_with_a_body_when_general_exception_occurs() {
        Exception exception = new Exception();
        Mockito.when(errorMessageLocalizationService.getLocaleMessage("system.error")).thenReturn("999;system error");

        ResponseEntity<ErrorResponse> response = restControllerExceptionHandler.handleException(exception);

        // assertions
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        ErrorResponse errorResponse = response.getBody();
        assertThat(errorResponse).isNotNull();
        assertThat(errorResponse.getLocale()).isEqualTo("en");
        assertThat(errorResponse.getErrorCode()).isEqualTo("999");
        assertThat(errorResponse.getErrorMessage()).isEqualTo("system error");
    }
}