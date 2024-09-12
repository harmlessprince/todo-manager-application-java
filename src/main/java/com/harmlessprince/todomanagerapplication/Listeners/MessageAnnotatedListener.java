package com.harmlessprince.todomanagerapplication.Listeners;

import com.harmlessprince.todomanagerapplication.Events.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MessageAnnotatedListener {
    private static final Logger logger = LoggerFactory.getLogger(MessageAnnotatedListener.class);

    @EventListener(MessageEvent.class)
    public void listenMessageEvent(MessageEvent event) {
        logger.info("MessageAnnotatedListener: Event received, content = {}, timestamp {}", event.getMessage(), event.getTimestamp());
    }

}
