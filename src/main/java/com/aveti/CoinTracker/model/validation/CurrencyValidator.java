package com.aveti.CoinTracker.model.validation;

import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<IsValidCurrency, String> {

    private String messageIfBlank;
    private String messageIfNotFound;
    private boolean nullable;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public void initialize(final IsValidCurrency field) {
        messageIfBlank = field.messageIfBlank();
        messageIfNotFound = field.messageNotFound();
        nullable = field.nullable();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (nullable) {
            if (value.isBlank()) {
                return true;
            } else if (currencyRepository.existsById(value)) {
                return true;
            } else {
                context.buildConstraintViolationWithTemplate(messageIfNotFound).addConstraintViolation();
                return false;
            }
        } else {
            if (value.isBlank()) {
                context.buildConstraintViolationWithTemplate(messageIfBlank).addConstraintViolation();
                return false;
            } else if (currencyRepository.existsById(value)) {
                return true;
            } else {
                context.buildConstraintViolationWithTemplate(messageIfNotFound).addConstraintViolation();
                return false;
            }
        }
    }
}
