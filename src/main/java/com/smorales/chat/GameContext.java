// package com.backbase.kalah.entity.statemachine;
package com.smorales.chat;


// import com.backbase.kalah.entity.Player;
// import com.backbase.kalah.entity.PlayerMove;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class GameContext {

    static final String CAN_NOT_ADD_MORE_PLAYERS = "Can not add more players";
    static final int NUMBER_OF_PLAYERS = 100;

    // private final State standbyState;
    // private final State playerTurn;
    // private State state;

    private int nextTurn;
    private Player currentPlayer;
    private final List<Player> players;

    @Inject
    Logger logger;

    public GameContext() {
        // standbyState = new StandBy();
        // playerTurn = new PlayerTurn();

        // state = standbyState;
        nextTurn = 1;
        players = new ArrayList<>(NUMBER_OF_PLAYERS);
        currentPlayer = null;
    }

    public void startGame() {
        logger.info("Initializing game...");
        // state.initialize(this);
    }

    // public void playerMove(PlayerMove playermove) {
    //     logger.info("User has made his move: " + playermove);
    //     state.playTurn(this);
    // }

    void assignTurnsToPlayers() {
        logger.info("Assigning turns to players");
        if (this.players.size() != NUMBER_OF_PLAYERS) {
            throw new IllegalStateException("Expected Two players, but found: " + this.players.size());
        }
        int random = new Random().nextInt(this.players.size()) + 1;
        switch (random) {
            case 1:
                // players.get(0).setTurn(1);
                // players.get(1).setTurn(2);
                this.currentPlayer = players.get(0);
                break;
            case 2:
                // players.get(0).setTurn(2);
                // players.get(1).setTurn(1);
                this.currentPlayer = players.get(1);
                break;
            default:
                throw new IllegalStateException("Unexpected value for random number: " + random);
        }
    }

    // public void calculateNextTurn() {
    //     if (this.currentPlayer.getTurn() == 1) {
    //         this.nextTurn = 2;
    //     } else if (this.currentPlayer.getTurn() == 2) {
    //         this.nextTurn = 1;
    //     }
    // }

    public void addPlayerToGame(String sessionId) {
        if (this.players.size() == NUMBER_OF_PLAYERS) {
            throw new IllegalStateException(CAN_NOT_ADD_MORE_PLAYERS);
        }
        Player player = new Player(sessionId);
        this.players.add(player);
    }

    public boolean isGameFull() {
//        return players.size() == NUMBER_OF_PLAYERS;
        return false; // never full, always welcome!
    }

    public boolean isPlayerOnGame(String sessionId) {
        return players.stream().anyMatch(p -> p.getSessionId().equals(sessionId));
    }

    // getters and setters
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public int getNextTurn() {
        return nextTurn;
    }

    public void setNextTurn(int nextTurn) {
        this.nextTurn = nextTurn;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    // public State getStandbyState() {
    //     return standbyState;
    // }

    // public State getPlayerTurn() {
    //     return playerTurn;
    // }

    // public State getState() {
    //     return state;
    // }

    // public void setState(State newState) {
    //     this.state = newState;
    // }

}
