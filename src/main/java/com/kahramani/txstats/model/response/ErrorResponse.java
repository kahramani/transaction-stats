package com.kahramani.txstats.model.response;

public final class ErrorResponse {

    private final String errorCode;
    private final String errorMessage;
    private final String locale;

    public ErrorResponse(String errorCode, String errorMessage, String locale) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.locale = locale;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getLocale() {
        return locale;
    }
}
