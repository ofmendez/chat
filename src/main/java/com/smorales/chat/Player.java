package com.smorales.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {

    public static final int PITS_PER_PLAYER = 6;

    private final String sessionId;
    // private Integer turn;
    // private final List<Pit> pits;

    public Player(String sessionId) {
        this.sessionId = sessionId;
        // this.pits = new ArrayList<>(PITS_PER_PLAYER);
    }

    public String getSessionId() {
        return sessionId;
    }

    // public Integer getTurn() {
    //     return turn;
    // }

    // public void setTurn(Integer turn) {
    //     this.turn = turn;
    // }

    // public List<Pit> getPits() {
    //     // return pits;
    // }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.sessionId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.sessionId, other.sessionId)) {
            return false;
        }
        return true;
    }

}
