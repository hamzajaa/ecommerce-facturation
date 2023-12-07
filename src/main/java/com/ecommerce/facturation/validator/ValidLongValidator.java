package com.ecommerce.facturation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidLongValidator implements ConstraintValidator<ValidLongId, Long>{

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null;
    }
}
