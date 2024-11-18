package org.app.battleshiproyale.service;

import org.app.battleshiproyale.model.GameStateDTO;
import org.app.battleshiproyale.model.HitResultDTO;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    public GameStateDTO generateGame(String sessionId) {
        return new GameStateDTO();
    }

    public HitResultDTO hit(String sessionId, String playerId) {
        return new HitResultDTO();
    }

    public GameStateDTO getGameState(String sessionId) {
        return new GameStateDTO();
    }
}
