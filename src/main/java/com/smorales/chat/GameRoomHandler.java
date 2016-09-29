// package com.backbase.kalah.boundary.websocket;
package com.smorales.chat;


// import com.backbase.kalah.control.GameCache;
// import com.backbase.kalah.entity.Player;
// import com.backbase.kalah.entity.PlayerMove;
// import com.backbase.kalah.entity.statemachine.GameContext;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ServerEndpoint("/gameroom")
public class GameRoomHandler {

    @Inject
    GameCache gameCache;

    @Inject
    Logger logger;

    @OnOpen
    public void connect(Session session) {
        String roomId = session.getQueryString();
        logger.info("Websocket was Connected \n  Room: " + roomId + "\n  SessionId: " + session.getId());

        GameContext context = gameCache.get(roomId);
        if (context == null) {
            context = new GameContext();
            context.addPlayerToGame(session.getId());
            gameCache.save(roomId, context);
            return;
        }

        if (context.isGameFull()) {
            if (!context.isPlayerOnGame(session.getId())) {
                throw new IllegalStateException("User is trying to access a full room");
            }
        } else {
            if (!context.isPlayerOnGame(session.getId())) {
                context.addPlayerToGame(session.getId());
                gameCache.save(roomId, context);
            }

            if (context.isGameFull()) {
                context.startGame();
                gameCache.save(roomId, context);
            }
        }
    }

    @OnClose
    public void close(Session session) {
        String roomId = session.getQueryString();
        logger.info("Websocket was closed, closing room: " + roomId);
        gameCache.remove(roomId);
    }

    @OnMessage
    public void onMessage(String msg, Session session) {
        logger.info("Received msg ");

        String roomId = session.getQueryString();
        GameContext context = gameCache.get(roomId);

        if (context == null) {
            throw new IllegalStateException("Inexistent context for roomId: " + roomId);
        }

        if (!context.isGameFull()) {
            sendMessageToPlayers(context, session, "Waiting for other player to connect");
            return;
        }

        if (context.isPlayerOnGame(session.getId())) {
            // context.playerMove(new PlayerMove(msg));
            sendMessageToPlayers(context, session, msg);
        }
    }

    private void sendMessageToPlayers(GameContext context, Session session, String msg) {
        Set<String> sessionIds = context.getPlayers().stream()
                .map(Player::getSessionId)
                .collect(Collectors.toSet());

        Set<Session> players = new HashSet<>();
        for (String id : sessionIds) {
            Set<Session> sessions = session.getOpenSessions().stream()
                    .filter(s -> s.getId().equals(id))
                    .collect(Collectors.toSet());
            players.addAll(sessions);
        }

        try {
            for (Session playerSession : players) {
                playerSession.getBasicRemote().sendText(msg);
            }
        } catch (IOException ioe) {
            logger.severe("IOException sending msg to players");
        }
    }

}
