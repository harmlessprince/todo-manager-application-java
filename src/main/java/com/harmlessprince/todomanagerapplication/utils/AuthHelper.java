package com.harmlessprince.todomanagerapplication.utils;

import com.harmlessprince.todomanagerapplication.interfaces.AuthHelperInterface;
import com.harmlessprince.todomanagerapplication.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class AuthHelper implements AuthHelperInterface {


    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getName() {
        return getAuthentication().getName();
    }

    @Override
    public User userDetails() {
        return (User) getAuthentication().getPrincipal();
    }
}


