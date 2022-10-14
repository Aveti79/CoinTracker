package com.aveti.CoinTracker.util;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.util.NumberPointType;
import org.thymeleaf.util.NumberUtils;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Custom implementation of numbers.formatDecimal() from Thymleaf,
 * with added option of cutting unnecessary zeros after meaning decimals.
 */
@Service
public class DecimalFormatter {

    private final Locale locale = LocaleContextHolder.getLocale();

    public DecimalFormatter() {
    }

    public String formatDecimalStripTrailingZeros(final Number target, final Integer minIntegerDigits, Integer decimalDigits) {
        if (target == null) {
            return null;
        }
        try {
            if (target.intValue()>=10) {
                decimalDigits=4;
            }
            if (target.intValue()>=100) {
                decimalDigits=2;
            }
            BigDecimal result = new BigDecimal(NumberUtils.format(target, minIntegerDigits, decimalDigits, NumberPointType.POINT, this.locale));
            return result.stripTrailingZeros().toPlainString();
        } catch (final Exception e) {
            throw new TemplateProcessingException(
                    "Error formatting decimal with minimum integer digits = " + minIntegerDigits +
                            " and decimal digits " + decimalDigits, e);
        }
    }
}
