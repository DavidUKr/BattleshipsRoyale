package org.app.battleshiproyale.model;

import lombok.Getter;
import org.app.battleshiproyale.game.game_elements.GridCell;

@Getter
public class GameStateDTO {

    private GridCell[][] grid;
    private boolean isEnd;

    public GameStateDTO(GridCell[][] grid, boolean isEnd) {
        this.grid = grid;
        this.isEnd = isEnd;
    }

}
