package org.app.battleshiproyale.game.game_elements;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GridCell {
    public Lock cellLock = new ReentrantLock();
    public int cellType;

    public GridCell(int cellType){
        this.cellType = cellType;
    }
}
