package com.aveti.CoinTracker.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CurrencyValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidCurrency {
    String message() default "Currency not found in database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
