package org.app.battleshiproyale.service;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.game.Game;
import org.app.battleshiproyale.model.PlayerMapDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class SessionService {

    public AtomicInteger playersJoined = new AtomicInteger(0);
    public ConcurrentLinkedQueue<String> playerIds = new ConcurrentLinkedQueue<>();

    private final Game game;

    public boolean joinPlayerToBattle(String playerId) {
        List<String> joinedPlayers = game.getJoinedPlayersIds();
        if (joinedPlayers.size() < 2  && !joinedPlayers.contains(playerId)) {
            game.joinPlayer(playerId);
            return true;
        }

        return false;
    }

    //TODO change to List<String>
    public String[] getJoinedPlayerIds() {
        return game.getJoinedPlayersIds().toArray(new String[0]);
    }

    public boolean isGameReady() {
        return game.getJoinedPlayersIds().size() == 2;
    }

    public void placePlayerShips(String playerId, PlayerMapDTO playerMapDTO) {
        //TODO DTO->PlayerMap
    }

    public boolean getPlayersReadyStatus() {
        return false;
    }

    public void resetSession() {
        game.resetSession();
    }
}
