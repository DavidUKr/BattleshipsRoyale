package org.app.battleshiproyale.model;

import lombok.Data;
import org.app.battleshiproyale.game.game_elements.GridCell;

import java.util.List;

@Data
public class GameState {
    Integer MAX_X=55;
    Integer MAX_Y=33;
    GridCell[][] mainGrid;
    List<Player> players;

    public GameState(GridCell[][] mainGrid, List<Player> players) {
        this.mainGrid = mainGrid;
        this.players = players;
    }
}
