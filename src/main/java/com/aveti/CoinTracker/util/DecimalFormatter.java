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
            BigDecimal result = new BigDecimal(NumberUtils.format(target, minIntegerDigits, calculateNumberOfDecimalDigits(target), NumberPointType.POINT, this.locale));
            return result.stripTrailingZeros().toPlainString();
        } catch (final Exception e) {
            throw new TemplateProcessingException(
                    "Error formatting decimal with minimum integer digits = " + minIntegerDigits +
                            " and decimal digits " + decimalDigits, e);
        }
    }

    private int calculateNumberOfDecimalDigits(Number target) {
        if (target.intValue()>=1 && target.intValue()<10) {
            return 6;
        } else if (target.intValue()>=10 && target.intValue()<100) {
            return 4;
        }
        else if (target.intValue()>=100) {
            return 2;
        }
        return 8;
    }
}
