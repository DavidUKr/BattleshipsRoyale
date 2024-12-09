package org.app.battleshiproyale.game.game_elements;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GridCell {
    public Lock cellLock = new ReentrantLock();
    public int cellType;
    public String ship_id; //Nullable


    public GridCell(int cellType){
        this.cellType = cellType;
    }

    public GridCell(int cellType, String ship_id){
        this.cellType = cellType;
        this.ship_id = ship_id;
    }
}
