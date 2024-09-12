package com.harmlessprince.todomanagerapplication.Listeners;

import com.harmlessprince.todomanagerapplication.Events.MessageEvent;
import com.harmlessprince.todomanagerapplication.Events.UserLoginEvent;
import com.harmlessprince.todomanagerapplication.config.CustomUserDetailService;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class UserLoggedInListener {
    private final CustomUserDetailService userDetailService;

    public UserLoggedInListener(CustomUserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @EventListener(UserLoginEvent.class)
    public void listenMessageEvent(@NotNull UserLoginEvent event) {
        userDetailService.updateLasLogin(event.getUser());
    }
}
