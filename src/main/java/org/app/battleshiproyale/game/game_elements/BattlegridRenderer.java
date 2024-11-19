package org.app.battleshiproyale.game.game_elements;

public class BattlegridRenderer implements Runnable {

    private final BattleGrid battleGrid;

    public BattlegridRenderer(BattleGrid battleGrid) {
        this.battleGrid = battleGrid;
    }

    @Override
    public void run() {
        // Continuously render the grid while the game is not finished
        while (!battleGrid.isFinished()) {
            // Clear the console (this works in most terminal environments)
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Print Player 1's 10x10 grid
            System.out.println("Player 1's Grid:");
            battleGrid.printPlayerGrid(battleGrid.getPlayer1Grid(), 0);

            // Print Player 2's 10x10 grid
            System.out.println("\nPlayer 2's Grid:");
            battleGrid.printPlayerGrid(battleGrid.getPlayer2Grid(), 1);

            // Print the Main 100x100 grid
            System.out.println("\nMain Grid:");
            battleGrid.printMainGrid();

            // Pause for a short duration to simulate rendering
            try {
                Thread.sleep(500); // 500 ms delay between renders
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }

        // Print the final state of the grids after the game ends
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

