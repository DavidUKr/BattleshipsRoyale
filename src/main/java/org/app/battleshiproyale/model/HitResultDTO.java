package org.app.battleshiproyale.model;


import lombok.Getter;
import org.app.battleshiproyale.game.game_elements.GridCell;

@Getter
public class HitResultDTO {
    private GridCell.CellType cellType;

    public HitResultDTO(GridCell.CellType cellType) {
        this.cellType = cellType;
    }

    public static HitResultDTO fromCode(int cellTypeCode) {
        return new HitResultDTO(GridCell.CellType.fromCode(cellTypeCode));
    }
}
