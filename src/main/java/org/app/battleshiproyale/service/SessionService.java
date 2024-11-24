package org.app.battleshiproyale.service;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.game.Game;
import org.app.battleshiproyale.model.PlayerMapDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final Game game;

    public boolean joinPlayerToBattle(String playerId) {
        return game.joinPlayer(playerId);
    }

    public void placePlayerShips(String playerId, PlayerMapDTO playerMapDTO) {
        game.placePlayerShips(playerId, playerMapDTO);
        game.setPlayerReady(playerId);
    }

    public Boolean getAllPlayersJoinedStatus() {
        return game.getAllPlayersJoinedCount();
    }

    public Boolean getAllPlayersReadyStatus() {
        return game.getAllPlayersReadyCount();
    }
}
