package com.harmlessprince.todomanagerapplication.Events;

import com.harmlessprince.todomanagerapplication.models.User;
import org.springframework.context.ApplicationEvent;

public class UserLoginEvent extends ApplicationEvent {

    private final User user;

    public UserLoginEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
