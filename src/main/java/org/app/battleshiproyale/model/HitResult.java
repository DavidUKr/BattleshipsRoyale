package org.app.battleshiproyale.model;

import lombok.Getter;
import org.app.battleshiproyale.game.game_elements.GridCell.CellType;

@Getter
public class HitResult {
    private CellType cellType;

    public HitResult(){}

    public HitResult(CellType cellType) {
        this.cellType = cellType;
    }

    public HitResult(boolean b, String s) {
    }
}
