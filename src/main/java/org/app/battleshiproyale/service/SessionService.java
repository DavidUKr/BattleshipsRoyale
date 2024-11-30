package org.app.battleshiproyale.service;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.game.Game;
import org.app.battleshiproyale.model.PlayerMap;
import org.app.battleshiproyale.model.PlayerMapDTO;
import org.app.battleshiproyale.utils.MapUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class SessionService {

    public ConcurrentLinkedQueue<String> playerReadyIds = new ConcurrentLinkedQueue<>();

    private final Game game;
    private final MapUtils mapUtils;

    public boolean joinPlayerToBattle(String playerId) {
        List<String> joinedPlayers = game.getJoinedPlayersIds();
        if (joinedPlayers.size() < 2  && !joinedPlayers.contains(playerId)) {
            game.joinPlayer(playerId);
            return true;
        }

        return false;
    }

    public List<String> getJoinedPlayerIds() {
        return game.getJoinedPlayersIds();
    }

    public boolean isGameReady() {
        return game.getJoinedPlayersIds().size() == 2;
    }

    public void placePlayerShips(String playerId, PlayerMapDTO playerMapDTO) {
        PlayerMap playerMap=mapUtils.getPlayerMapFromDTO(playerMapDTO, playerId);
        game.placePlayerShips(playerId, playerMap);
        game.setPlayerReady(playerId);
        //TODO remove provisional logic
        playerReadyIds.add(playerId);
    }

    public String[] getPlayersReadyStatus() {
//        return game.getReadyPlayersIds();
        //TODO remove provisional logic
        return playerReadyIds.toArray(new String[0]);
    }

    public void resetSession() {
        game.resetSession();
    }
}
