package org.app.battleshiproyale.game.game_elements;
import java.util.Random;

class PlayerInput implements Runnable {
    // Attributes
    int playerId;  // Player number (1 or 2)
    BattleGrid grid;
    Random random = new Random();  // Create a Random object

    // Constructor
    PlayerInput(int playerId, BattleGrid grid) {
        this.playerId = playerId;
        this.grid = grid;
    }

    // Run method: continuously get random input from the player
    @Override
    public void run() {
        while (true) {
            // Generate random coordinates
            int x = random.nextInt(grid.gridSize);  // Random x-coordinate
            int y = random.nextInt(grid.gridSize);  // Random y-coordinate

            // Mark the square for the player
            System.out.println("Player " + playerId + " randomly marked (" + x + ", " + y + ")");
            grid.markSquare(playerId, x, y);  // Mark the square

            // Immediately refresh the grid after the move
            grid.clearScreen();  // Clear the screen before printing the updated grid
            grid.printGrids();   // Refresh the grid for both players

            // Sleep to simulate time between moves
            try {
                Thread.sleep(1000);  // Wait for 1 second before the next move
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

