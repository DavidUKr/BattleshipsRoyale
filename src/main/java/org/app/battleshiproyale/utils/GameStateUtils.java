package org.app.battleshiproyale.utils;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.game.game_elements.GridCell;
import org.app.battleshiproyale.model.GameState;
import org.app.battleshiproyale.model.GameStateDTO;
import org.app.battleshiproyale.model.Player;
import org.app.battleshiproyale.model.PlayerDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GameStateUtils {
    private final PlayerUtils playerUtils;
    private final MapUtils mapUtils;

    public GameStateDTO getDTOfromGameState(GameState gameState, String forPlayerId) {

        GameStateDTO gameStateDTO = new GameStateDTO();
        if(forPlayerId.equals(gameState.getPlayers().get(0).getId()))
            gameStateDTO.setMainGrid(mapUtils.getDTOfromGridMatrix(gameState.getMainGrid(), 0, gameState.getMAX_X(), gameState.getMAX_Y()));
        else if(forPlayerId.equals(gameState.getPlayers().get(1).getId()))
            gameStateDTO.setMainGrid(mapUtils.getDTOfromGridMatrix(gameState.getMainGrid(), 1, gameState.getMAX_X(), gameState.getMAX_Y()));
        else
            return gameStateDTO;

        List<PlayerDTO> playerDTOs = new ArrayList<>();
        for (Player p : gameState.getPlayers()) {
            playerDTOs.add(playerUtils.getDTOfromPlayerWShips(p));
        }
        return gameStateDTO;
    }
}
