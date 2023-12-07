package com.ecommerce.facturation.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidLongValidator.class)
@Documented
public @interface ValidLongId {
    String message() default "Invalid value. ID must be a valid Long.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
