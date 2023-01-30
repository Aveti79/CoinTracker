package com.aveti.CoinTracker.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CurrencyValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidCurrency {

    String message() default "Wrong data of currency field";

    String messageNotFound() default "Currency not found in database";

    String messageIfBlank() default "Currency field can't be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Determines if currency field can be null.
     */
    boolean nullable() default false;
}
