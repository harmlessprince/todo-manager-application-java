package com.harmlessprince.todomanagerapplication.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    private final DateTimeFormatter formatter;

    public StringToLocalDateTimeConverter(String dateFormatPattern) {
        this.formatter = DateTimeFormatter.ofPattern(dateFormatPattern);
    }

    @Override
    public LocalDateTime convert(String source) {
        if (source.trim().isEmpty()) {
            return null;
        }

        return LocalDateTime.parse(source, formatter);
    }
}
