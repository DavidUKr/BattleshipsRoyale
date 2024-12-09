package org.app.battleshiproyale.model;

import org.app.battleshiproyale.game.game_elements.GridCell;

public class HitResult {
    private GridCell.CellType cellType;

    public HitResult(GridCell.CellType cellType) {
        this.cellType = cellType;
    }

    public static HitResult fromCode(int cellTypeCode) {
        return new HitResult(GridCell.CellType.fromCode(cellTypeCode));
    }
}
