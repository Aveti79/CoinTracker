package com.aveti.CoinTracker.util;

import org.springframework.stereotype.Service;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.util.StringUtils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Custom implementation of numbers.formatCurrency() from Thymleaf,
 * with added dynamic precision for small numbers.
 */
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
        var nf = NumberFormat.getCurrencyInstance(locale);
        if (Math.abs(target.doubleValue())<=0.1) {
            nf.setMaximumFractionDigits(4);
        }
        if (Math.abs(target.doubleValue())<=0.01) {
            nf.setMaximumFractionDigits(8);
        }
        try {
            return StringUtils.replace(nf.format(target), ",", " ");
        } catch (final Exception e) {
            throw new TemplateProcessingException("Error formatting currency", e);
        }
    }
}
