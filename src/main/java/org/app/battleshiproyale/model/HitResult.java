package org.app.battleshiproyale.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.game.game_elements.GridCell.CellType;

@Getter
public class HitResult {
    private CellType cellType;
    String message;

    public HitResult(CellType cellType, String s) {
        this.cellType = cellType;
        this.message = s;
    }
}
