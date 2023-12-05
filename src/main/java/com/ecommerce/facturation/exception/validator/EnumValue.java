package com.ecommerce.facturation.exception.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValue {
    String message() default "Invalid enum value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class EnumValueValidator implements ConstraintValidator<EnumValue, Enum<?>> {
    @Override
    public void initialize(EnumValue constraintAnnotation) {
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        return value != null;
    }
}