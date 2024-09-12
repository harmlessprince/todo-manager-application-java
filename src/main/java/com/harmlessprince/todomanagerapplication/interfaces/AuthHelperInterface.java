package com.harmlessprince.todomanagerapplication.interfaces;

import com.harmlessprince.todomanagerapplication.models.User;
import org.springframework.security.core.Authentication;

public interface AuthHelperInterface {
    Authentication getAuthentication();
    public String getName();
    public User userDetails();
}
