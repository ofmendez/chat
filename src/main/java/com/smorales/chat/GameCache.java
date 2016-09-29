// package com.backbase.kalah.control;
package com.smorales.chat;


// import com.backbase.kalah.entity.statemachine.GameContext;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.inject.Singleton;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class GameCache {

    private ConcurrentHashMap<String, GameContext> games = null;

    @PostConstruct
    public void initialize() {
        games = new ConcurrentHashMap<>();
    }

    public void save(String roomId, GameContext context) {
        games.put(roomId, context);
    }

    public GameContext get(String roomId) {
        return games.get(roomId);
    }

    public void remove(String roomId) {
        games.remove(roomId);
    }
}
