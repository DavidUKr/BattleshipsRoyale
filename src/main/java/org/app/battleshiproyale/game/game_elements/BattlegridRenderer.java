package org.app.battleshiproyale.game.game_elements;

public class BattlegridRenderer implements Runnable {

    public BattleGrid battleGrid;

    public BattlegridRenderer(BattleGrid battleGrid) {
        this.battleGrid = battleGrid;
    }

    @Override
    public void run() {
        while(!battleGrid.isFinished()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            battleGrid.printGrid(0);
            battleGrid.printGrid(1);
        }

        battleGrid.printGrid(0);
        battleGrid.printGrid(1);
    }
}
