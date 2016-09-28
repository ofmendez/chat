package com.smorales.chat;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
@ServerEndpoint("/echo")
public class EchoWebSocketServer {

    private static final Logger logger = Logger.getLogger(EchoWebSocketServer.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        logger.log(Level.INFO, "Opening Session {0}", session.getId());
    }

    @OnClose
    public void onClose(Session session) {
        logger.log(Level.INFO, "Closing Session {0}", session.getId());
    }

    @OnError
    public void onError(Throwable t) {
        logger.log(Level.INFO, "onError", t);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            logger.log(Level.INFO, "Received Message on Session {0}", session.getId());
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

}