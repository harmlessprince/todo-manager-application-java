package com.harmlessprince.todomanagerapplication.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class HandleAccessDeniedException implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(HandleAccessDeniedException.class);
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.error("Unauthorized error : {}", accessDeniedException.getMessage());
        response.addHeader("access_denied_reason", "Not Authorized");
//        response.sendError(403, "Access Denied");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_FORBIDDEN);
        body.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        body.put("error", "unauthorized");
        body.put("message", "You are not authorized to perform this action");
        final ObjectMapper mapper = new ObjectMapper();
//        System.out.println(response.getWriter());
        mapper.writeValue(response.getOutputStream(), body);
    }
}
