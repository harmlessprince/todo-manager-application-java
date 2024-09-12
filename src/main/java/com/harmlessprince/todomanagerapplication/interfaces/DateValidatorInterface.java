package com.harmlessprince.todomanagerapplication.interfaces;

import org.springframework.stereotype.Service;

@Service
public interface DateValidatorInterface {
     boolean isInvalid(String dateString);
}
