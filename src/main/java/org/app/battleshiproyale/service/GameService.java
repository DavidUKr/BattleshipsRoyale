package org.app.battleshiproyale.service;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.game.Game;
import org.app.battleshiproyale.model.GameStateDTO;
import org.app.battleshiproyale.model.HitDTO;
import org.app.battleshiproyale.model.HitResultDTO;
import org.app.battleshiproyale.model.PerkDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final Game game;

    public GameStateDTO generateGame(String sessionId) {
        return game.resetBoard();
    }

    public HitResultDTO hit(String player_id, HitDTO hitDTO) {
        return game.hit(player_id, hitDTO);
    }

    public GameStateDTO getGameState(String sessionId) {
        return new GameStateDTO();
    }

    public boolean usePerk(String playerId, PerkDTO perkDTO) {
        return game.usePerk(playerId, perkDTO);
    }

    public int getPlayerStamina(String playerId) {
        return game.getPlayerStamina(playerId);
    }
}
