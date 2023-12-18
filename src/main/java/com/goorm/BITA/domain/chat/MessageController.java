package com.goorm.BITA.domain.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.DestinationVariable;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final SimpMessagingTemplate template;
    @MessageMapping("/messages/{roomId}")
    public void addPlayers(@DestinationVariable long roomId) {
        template.convertAndSend("/sub/messages"+roomId);
    }

    @MessageMapping("/messages/{roomId}/test")
    public void addPlayer(@DestinationVariable long roomId, @Payload String sdp) {
        template.convertAndSend("/sub/messages"+roomId, sdp);
    }
}