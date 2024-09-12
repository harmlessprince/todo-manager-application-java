package com.harmlessprince.todomanagerapplication.validators.interfaces;

import com.harmlessprince.todomanagerapplication.validators.constraints.CustomDateTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomDateTimeValidator.class)
@Documented
public @interface CustomDateTime {
    String message() default "Invalid case provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

//    String value();

    String format();
}
