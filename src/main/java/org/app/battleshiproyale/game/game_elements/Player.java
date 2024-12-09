package org.app.battleshiproyale.game.game_elements;

import lombok.Getter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player implements Runnable {
    @Getter
    private final int playerId;
    private final Random random = new Random(); // For generating random moves
    private final BattleGrid battleGrid;
    private final Set<String> visitedCells = new HashSet<>();  // Track visited cells

    public Player(int playerId, BattleGrid battleGrid) {
        this.playerId = playerId;
        this.battleGrid = battleGrid;
    }

    @Override
    public void run() {
        // Determine which grid this player interacts with
        GridCell[][] playerGrid = (playerId == 0) ? battleGrid.getPlayer1Grid() : battleGrid.getPlayer2Grid();
        int gridWidth = playerGrid.length;
        int gridHeight = playerGrid[0].length;

        // Continue playing until all cells are visited or the game is finished
        while (visitedCells.size() < gridWidth * gridHeight && !battleGrid.isFinished()) {
            int x = random.nextInt(gridWidth);  // Generate random row index
            int y = random.nextInt(gridHeight);  // Generate random column index
            String cellKey = x + "," + y;      // Unique identifier for the cell

            // Skip this cell if it has already been visited
            if (visitedCells.contains(cellKey)) {
                continue;
            } else {
                visitedCells.add(cellKey);
            }

            try {
                battleGrid.gridLock.lock(); // Lock the grid before hitting
                boolean hitResult = battleGrid.hit(x, y, playerId, playerGrid, gridWidth, gridHeight);
                if (hitResult) {
                    System.out.println("Player " + (playerId + 1) + " successfully hit at (" + x + ", " + y + ")");
                } else {
                    System.out.println("Player " + (playerId + 1) + " missed at (" + x + ", " + y + ")");
                }
            } finally {
                battleGrid.gridLock.unlock();
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}



