package org.app.battleshiproyale.game.game_elements;

public class BattlegridRenderer implements Runnable {

    private final BattleGrid battleGrid;

    public BattlegridRenderer(BattleGrid battleGrid) {
        this.battleGrid = battleGrid;
    }

    @Override
    public void run() {
        while (!battleGrid.isFinished()) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Player 1's Grid:");
            battleGrid.printPlayerGrid(battleGrid.getPlayer1Grid(), 0);

            System.out.println("\nPlayer 2's Grid:");
            battleGrid.printPlayerGrid(battleGrid.getPlayer2Grid(), 1);

            System.out.println("\nMain Grid:");
            battleGrid.printMainGrid();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("Final Player 1's Grid:");
        battleGrid.printPlayerGrid(battleGrid.getPlayer1Grid(), 0);

        System.out.println("\nFinal Player 2's Grid:");
        battleGrid.printPlayerGrid(battleGrid.getPlayer2Grid(), 1);

        System.out.println("\nFinal Main Grid:");
        battleGrid.printMainGrid();
    }
}

