package com.thucnh96.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PushMessageService {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;


    public void sendMessage(@DestinationVariable String text) {
        messagingTemplate.convertAndSend("/channel/progress", text);
    }
}
