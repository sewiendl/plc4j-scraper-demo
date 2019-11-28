package com.example.plc4jscraperdemo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.plc4x.java.scraper.ResultHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class ScraperResultHandler extends TextWebSocketHandler implements ResultHandler {

    private String latest = "";

    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Override
    public void handle(String job, String alias, Map<String, Object> results) {
        latest = LocalDateTime.now() + " job=" + job + "; alias=" + alias + "; results=" + results;
        log.debug(latest);
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(latest));
                } catch (IOException e) {
                    log.warn(String.format("failed to send websocket message to %s", session), e);
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("afterConnectionEstablished {}", session);
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.debug("afterConnectionClosed {}", session);
        sessions.remove(session);
    }
}
