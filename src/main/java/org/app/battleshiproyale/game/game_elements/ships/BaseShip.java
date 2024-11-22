package org.app.battleshiproyale.game.game_elements.ships;

import lombok.Getter;
import lombok.Setter;
import org.app.battleshiproyale.game.game_elements.GridCell;

import java.util.List;

abstract public class BaseShip {
    @Getter
    protected int length;
    @Getter
    protected int damage=0;
    @Getter
    protected boolean isAlive=true;
    @Getter @Setter
    protected int team_id;
    @Getter @Setter
    private GridCell[][] grid;
    @Getter @Setter
    private List<int[]> coordinates;

    public BaseShip(int team_id) {
        this.team_id = team_id;
    }

    public boolean apply_damage() {
        if(damage==length-1) isAlive=false;
        else damage++;

        return isAlive;
    }

    public boolean isDestroyed() {

        for (int[] coord : coordinates) {
            int x = coord[0];
            int y = coord[1];
            // Check if the cell is not hit
            if (grid[x][y].cellType != 1 && grid[x][y].cellType != 2) {
                return false; // Ship is not completely destroyed
            }
        }
        return true; // All parts of the ship are hit
    }


}
