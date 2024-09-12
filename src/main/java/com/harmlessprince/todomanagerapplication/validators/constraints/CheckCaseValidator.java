package com.harmlessprince.todomanagerapplication.validators.constraints;

import com.harmlessprince.todomanagerapplication.utils.CaseMode;
import com.harmlessprince.todomanagerapplication.validators.interfaces.CheckCase;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckCaseValidator implements ConstraintValidator<CheckCase, String>{
    private CaseMode caseMode;

    @Override
    public void initialize(CheckCase constraintAnnotation) {
        this.caseMode = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null){
            return true;
        }
        if (caseMode == CaseMode.UPPER){
            return value.equals(value.toUpperCase());
        }else {
            return value.equals(value.toLowerCase());
        }
    }
}

