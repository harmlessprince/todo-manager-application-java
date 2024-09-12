package com.harmlessprince.todomanagerapplication.Publishers;

import com.harmlessprince.todomanagerapplication.Events.MessageEvent;
import com.harmlessprince.todomanagerapplication.Events.UserLoginEvent;
import com.harmlessprince.todomanagerapplication.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.w3c.dom.events.EventException;

@Service
public class TodoManagerPublisherService {
    private static final Logger logger = LoggerFactory.getLogger(TodoManagerPublisherService.class);

    private final ApplicationEventPublisher applicationEventPublisher;

    public enum Events {
        MESSAGE_EVENT,
        LOGIN_EVENT,
    }

    public TodoManagerPublisherService(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

//    public void fireMessageEvent(String message) {
//        logger.info("PUBLISHER: initializing Event has been published");
//        MessageEvent messageEvent = new MessageEvent(this, message);
//        applicationEventPublisher.publishEvent(messageEvent);
//        logger.info("PUBLISHER: Event has been published");
//    }

    public void fireLoginEvent(User user) {
        logger.info("PUBLISHER: initializing UserLoginEvent has been published");
        UserLoginEvent event = new UserLoginEvent(this, user);
        applicationEventPublisher.publishEvent(event);
        logger.info("PUBLISHER: UserLoginEvent has been published");
    }

    public <T> void handle(Events events, T data) throws Exception {
        switch (events) {
            case MESSAGE_EVENT -> {
                fireMessageEvent(data);
            }
            case LOGIN_EVENT -> {
                fireLoginEvent((User) data);
            }
            default -> throw new Exception("Invalid event type supplied");
        }
    }

    private <T> void fireMessageEvent(T message) {
        logger.info("PUBLISHER: initializing Event has been published");
        MessageEvent messageEvent = new MessageEvent(this, message.toString());
        applicationEventPublisher.publishEvent(messageEvent);
        logger.info("PUBLISHER: Event has been published");
    }
}
