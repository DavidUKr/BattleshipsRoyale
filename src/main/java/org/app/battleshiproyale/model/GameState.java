package org.app.battleshiproyale.model;
import lombok.Data;
import org.app.battleshiproyale.game.game_elements.GridCell;

import java.util.List;

@Data
public class GameState {
    Integer MAX_X;
    Integer MAX_Y;
    GridCell[][] mainGrid;
    List<Player> players;
    private boolean isEnd;

    public GameState(GridCell[][] mainGrid, List<Player> players, boolean isEnd, int maxX, int maxY) {
        this.MAX_X = maxX;
        this.MAX_Y = maxY;
        this.mainGrid = mainGrid;
        this.players = players;
    }
}
