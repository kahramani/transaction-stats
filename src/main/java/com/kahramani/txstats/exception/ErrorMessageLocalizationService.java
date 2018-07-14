package com.kahramani.txstats.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Redundant to write tests for this class which has (nearly) no business logic, it will be much like testing spring resource bundling features
 */
@Service
public class ErrorMessageLocalizationService {

    private static final Logger logger = LoggerFactory.getLogger(ErrorMessageLocalizationService.class);

    private final ResourceBundleMessageSource messageSource;

    public ErrorMessageLocalizationService(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    String getLocaleMessage(String key, Object... args) {
        Locale locale = getLocale();
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException e) {
            logger.warn("{} not found in messages file", key, e);
            return messageSource.getMessage("system.error", args, locale);
        }
    }

    Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }
}
