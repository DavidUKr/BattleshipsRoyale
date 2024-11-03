package org.app.battleshiproyale.game.game_elements;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player implements Runnable {
    public final int playerId;
    private final Random random = new Random();
    public BattleGrid battleGrid;

    private final Set<String> visitedCells = new HashSet<>();

    public Player(int playerId, BattleGrid grid) {
        this.playerId = playerId;
        battleGrid = grid;
    }
    @Override
    public void run() {
        int gridRows = battleGrid.grid.length;
        int gridCols = battleGrid.grid[0].length;

        while (visitedCells.size() < gridRows * gridCols && !battleGrid.isFinished()) {
            int i = random.nextInt(gridRows-1);
            int j = random.nextInt(gridCols-1);
            String cellKey = i + "," + j;

            if (visitedCells.contains(cellKey)) {
                continue;
            }else{
                visitedCells.add(cellKey);
            }

            try {
                battleGrid.grid[i][j].cellLock.lock();
                battleGrid.hit(i,j, playerId);
            } finally {
                battleGrid.grid[i][j].cellLock.unlock();
            }

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
