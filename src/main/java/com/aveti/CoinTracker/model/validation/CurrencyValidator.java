package com.aveti.CoinTracker.model.validation;

import com.aveti.CoinTracker.model.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<IsValidCurrency, String> {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return currencyRepository.existsById(value);
    }
}
