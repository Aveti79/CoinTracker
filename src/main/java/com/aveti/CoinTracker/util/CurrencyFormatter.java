package com.aveti.CoinTracker.util;

import org.springframework.stereotype.Service;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.util.NumberUtils;

import java.util.Locale;

@Service
public class CurrencyFormatter {

    CurrencyFormatter() {
    }

    public String formatCurrency(final Number target, Locale locale) {
        if (target == null) {
            return null;
        } else if (locale == null) {
            return null;
        }
        try {
            return NumberUtils.formatCurrency(target, locale);
        } catch (final Exception e) {
            throw new TemplateProcessingException("Error formatting currency", e);
        }
    }
}
