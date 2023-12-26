package com.goorm.BITA.domain.chat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class SignalingController {
    // user id와 container id를 받아 offer 전달
    @MessageMapping("/peer/offer/{camKey}/{roomId}")
    @SendTo("/sub/peer/offer/{camKey}/{roomId}")
    public String PeerHandleOffer(
        @Payload String offer,
        @DestinationVariable(value = "roomId") String roomId,
        @DestinationVariable(value = "camKey") String camKey)
    {
        System.out.println("[OFFER] " + camKey + " : " + offer);
        log.info("[OFFER] " + camKey + " : " + offer);
        return offer;
    }
    @MessageMapping("/peer/iceCandidate/{camKey}/{roomId}")
    @SendTo("/sub/peer/iceCandidate/{camKey}/{roomId}")
    public String PeerHandleIceCandidate(
        @Payload String candidate,
        @DestinationVariable(value = "roomId") String roomId,
        @DestinationVariable(value = "camKey") String camKey)
    {
        System.out.println("[ICECANDIDATE] " + camKey + " : " + candidate);
        log.info("[ICECANDIDATE] " + camKey + " : " + candidate);
        return candidate;
    }
    @MessageMapping("/peer/answer/{camKey}/{roomId}")
    @SendTo("/sub/peer/answer/{camKey}/{roomId}")
    public String PeerHandleAnswer(
        @Payload String answer,
        @DestinationVariable(value = "roomId") String roomId,
        @DestinationVariable(value = "camKey") String camKey)
    {
        System.out.println("[ANSWER] " + camKey + " : " + answer);
        log.info("[ANSWER] " + camKey + " : " + answer);
        return answer;
    }
    //camKey 를 받기위해 신호를 보내는 webSocket
    @MessageMapping("/call/key")
    @SendTo("/sub/call/key")
    public String callKey(@Payload String message) {
        System.out.println("[Key] " + message);
        log.info("[Key] " + message);
        return message;
    }
    //자신의 camKey 를 모든 연결된 세션에 보내는 webSocket
    @MessageMapping("/send/key")
    @SendTo("/sub/send/key")
    public String sendKey(@Payload String message) {
        System.out.println("(send) [Key] " + message);
        log.info("(send) [Key] " + message);
        return message;
    }
}