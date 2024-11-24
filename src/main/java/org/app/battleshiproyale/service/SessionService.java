package org.app.battleshiproyale.service;

import org.app.battleshiproyale.model.PlayerMapDTO;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SessionService {

    public AtomicInteger playersJoined = new AtomicInteger(0);
    public ConcurrentLinkedQueue<String> playerIds = new ConcurrentLinkedQueue<>();


    public boolean joinPlayerToBattle(String playerId) {
        if (playersJoined.get() < 2  && !playerIds.contains(playerId)) {
            playerIds.add(playerId);
            playersJoined.incrementAndGet();
            return true;
        }

        return false;
    }

    public boolean isGameReady() {
        return playersJoined.get() == 2;
    }

    public void placePlayerShips(String playerId, PlayerMapDTO playerMapDTO) {

    }

    public int getPlayersJoinedStatus() {
        return playersJoined.get();
    }

    public boolean getPlayersReadyStatus() {
        return false;
    }

    public String[] getJoinedPlayerIds() {
        return playerIds.toArray(new String[0]);
    }

    public void resetSession() {
       
        playerIds.removeAll(playerIds);
        playerIds.clear();
        playersJoined.set(0);
    }
}
