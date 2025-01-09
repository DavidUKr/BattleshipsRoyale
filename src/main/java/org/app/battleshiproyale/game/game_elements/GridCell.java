package org.app.battleshiproyale.game.game_elements;

import lombok.Getter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GridCell {

    public String ship_id; //Nullable
    public Lock cellLock = new ReentrantLock();
    public CellType cellType;

    public GridCell(CellType cellType) {
        this.cellType = cellType;
    }

    public GridCell(CellType cellType, String ship_id) {
        this.cellType = cellType;
        this.ship_id = ship_id;
    }

    // Embedded Enum for Cell Types
    @Getter
    public enum CellType {
        DISCOVERED_EMPTY(0),
        DISCOVERED_SHIP_TEAM_1(1),
        DISCOVERED_SHIP_TEAM_2(2),
        UNDISCOVERED_EMPTY(3),
        UNDISCOVERED_SHIP_TEAM_1(4),
        UNDISCOVERED_SHIP_TEAM_2(5),
        UNDISCOVERED_PERK_1(6),
        UNDISCOVERED_PERK_2(7),
        INVALID(-1),
        HIT_ENEMY_SHIP(10);

        private final int code;

        CellType(int code) {
            this.code = code;
        }

        public static CellType fromCode(int code) {
            for (CellType type : CellType.values()) {
                if (type.code == code) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid cell type code: " + code);
        }
    }
}

