package com.harmlessprince.todomanagerapplication.Listeners;

import com.harmlessprince.todomanagerapplication.Events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MessageListener implements ApplicationListener<MessageEvent> {
    private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);
    @Override
    public void onApplicationEvent(MessageEvent event) {
        logger.info("MessageListener: Event received, content = " + event.getMessage() + ", timestamp " + event.getTimestamp());
    }
}
