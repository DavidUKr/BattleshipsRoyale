package org.app.battleshiproyale.game;

import org.app.battleshiproyale.game.game_elements.BattleGrid;
import org.app.battleshiproyale.game.game_elements.ships.*;

public class RunGame {
    public static void main(String[] args) {
        System.out.println("Running Game");

        BattleGrid battleGrid = new BattleGrid();

        printMap(battleGrid);

        battleGrid.hit(1,1);
        battleGrid.hit(7,1);
        battleGrid.hit(1,7);
        battleGrid.hit(2,2);
        battleGrid.hit(7,6);

        printMap(battleGrid);

    }

    private static void printMap(BattleGrid battleGrid) {
        battleGrid.printGrid(0);
        battleGrid.printGrid(1);
    }

}
