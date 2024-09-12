package com.harmlessprince.todomanagerapplication.controllers;

import com.harmlessprince.todomanagerapplication.Events.MessageEvent;
import com.harmlessprince.todomanagerapplication.Publishers.TodoManagerPublisherService;
import com.harmlessprince.todomanagerapplication.config.CustomUserDetailService;
import com.harmlessprince.todomanagerapplication.config.JwtService;
import com.harmlessprince.todomanagerapplication.dto.LoginRequestDto;
import com.harmlessprince.todomanagerapplication.models.User;
import com.harmlessprince.todomanagerapplication.utils.ApiResponseHelper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("auth")
public class AuthenticationController {



    private final AuthenticationManager authenticationManager;


    CustomUserDetailService userDetailService;

    PasswordEncoder passwordEncoder;

    JwtService jwtHelper;

    private final TodoManagerPublisherService publisherService;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailService userDetailService, PasswordEncoder passwordEncoder, JwtService jwtHelper, TodoManagerPublisherService publisherService) {
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
        this.publisherService = publisherService;
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponseHelper<User>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
//        user.getRole();
        String jwt = jwtHelper.generateToken(user);
        Map<String, Object> meta = new HashMap<>();
        meta.put("jwt", jwt);
//        ApiResponseHelper<User> loginSuccess = ApiResponseHelper.success(user, "Login Success", meta);
        publisherService.handle(TodoManagerPublisherService.Events.LOGIN_EVENT, user);
        return ResponseEntity.ok().body(ApiResponseHelper.success(user, "Login Success", meta));
    }

    @PostMapping("logout")
    public String logout(){
        return  "logout";
    }
}
