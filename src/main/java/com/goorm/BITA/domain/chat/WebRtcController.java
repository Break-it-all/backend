package com.goorm.BITA.domain.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WebRtcController {

    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/chat/{roomId}")
    public void handleSDP(@Payload String sdpMessage,
                          @Payload String iceMessage,
                          @Header("simpSessionId") String sessionId,
                          @Header("roomId") String roomId) {
        messagingTemplate.convertAndSend("/sub/chat/" + roomId);
    }

    @MessageMapping("/chat/{roomId}/ice")
    public void handleIceCandidate(@Payload String sdpMessage,
                                   @Header("simpSessionId") String sessionId,
                                   @Header("roomId") String roomId) {
        messagingTemplate.convertAndSend("/sub/chat/" + roomId, sdpMessage);
    }
}