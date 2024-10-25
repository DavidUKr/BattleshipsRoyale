package org.app.battleshiproyale.game.game_elements;

public class MainGame {
    public static void main(String[] args) throws InterruptedException {
        // Create a GameGrid object
        BattleGrid grid = new BattleGrid(5);  // 5x5 grid (you can change the size)

        // Print the initial grids before players make any move
        grid.clearScreen();
        grid.printGrids();

        // Create PlayerInput threads for Player 1 and Player 2
        Thread player1Thread = new Thread(new PlayerInput(1, grid));
        Thread player2Thread = new Thread(new PlayerInput(2, grid));

        // Start the threads
        player1Thread.start();
        player2Thread.start();
    }
}


