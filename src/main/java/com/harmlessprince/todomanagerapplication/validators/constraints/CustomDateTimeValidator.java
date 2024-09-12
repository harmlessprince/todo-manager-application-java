package com.harmlessprince.todomanagerapplication.validators.constraints;

import com.harmlessprince.todomanagerapplication.interfaces.DateValidatorInterface;
import com.harmlessprince.todomanagerapplication.utils.DateTimeValidator;
import com.harmlessprince.todomanagerapplication.validators.interfaces.CustomDateTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@NoArgsConstructor
public class CustomDateTimeValidator implements ConstraintValidator<CustomDateTime, String> {

    private String pattern;

//    private String value;
    @Override
    public void initialize(CustomDateTime constraintAnnotation) {
//        this.value = constraintAnnotation.value();
        this.pattern = constraintAnnotation.format();
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            // Define a custom DateTimeFormatter to match the format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.pattern);

            // Parse the string into a LocalDateTime object
            LocalDateTime customDateTime = LocalDateTime.parse(value, formatter);

            System.out.println(customDateTime);
        } catch (DateTimeParseException parseException) {
            System.out.println("Date Validation: " + parseException.getMessage());
            return false;
        }
        return true;
    }
}
