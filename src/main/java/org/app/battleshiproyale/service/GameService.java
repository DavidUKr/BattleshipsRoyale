package org.app.battleshiproyale.service;

import org.app.battleshiproyale.model.GameStateDTO;
import org.app.battleshiproyale.model.HitDTO;
import org.app.battleshiproyale.model.HitResultDTO;
import org.app.battleshiproyale.model.PerkDTO;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    public GameStateDTO generateGame(String sessionId) {
        return new GameStateDTO();
    }

    public HitResultDTO hit(String sessionId, HitDTO hitDTO) {
        return new HitResultDTO();
    }

    public GameStateDTO getGameState(String sessionId) {
        return new GameStateDTO();
    }

    public boolean usePerk(String playerId, PerkDTO perkDTO) {
        return true;
    }

    public int getPlayerStamina(String playerId) {
        return 100;
    }
}
