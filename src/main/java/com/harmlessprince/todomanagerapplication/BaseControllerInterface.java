package com.harmlessprince.todomanagerapplication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;

public interface BaseControllerInterface {
    public String  index(HttpServletRequest request);
    public String  show(HttpServletRequest request);

    public String  store(HttpServletRequest request);

    public String  destroy(HttpServletRequest request);
}
