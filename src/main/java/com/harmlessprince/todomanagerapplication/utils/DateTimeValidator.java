package com.harmlessprince.todomanagerapplication.utils;

import com.harmlessprince.todomanagerapplication.interfaces.DateValidatorInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



public class DateTimeValidator implements DateValidatorInterface {

    private final String dateFormat;

    public DateTimeValidator(String dateFormatInput) {
        this.dateFormat = dateFormatInput;
    }


    @Override
    public  boolean isInvalid(String dateString) {
        DateFormat simpleDateFormat = new SimpleDateFormat(this.dateFormat);
        simpleDateFormat.setLenient(false);
        try {
            LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }catch (DateTimeParseException parseException){
            System.out.println("Date Validation: " + parseException.getMessage());
            return true;
        }
        return false;
    }


}