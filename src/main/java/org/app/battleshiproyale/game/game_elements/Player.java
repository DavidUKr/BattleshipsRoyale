package org.app.battleshiproyale.game.game_elements;

public class Player implements Runnable {
    private final int playerId;

    public enum CellState {
        CELL_CLICKED,
        CELL_UNCLICKED;

        public int toInt() {
            switch (this) {
                case CELL_CLICKED:
                    return 1;
                case CELL_UNCLICKED:
                default:
                    return 0;
            }
        }
    }

    public Player(int playerId) {
        this.playerId = playerId;
    }
    @Override
    public void run() {
        for (int i = 0; i < BattleGame.grid.length; i++) {
            for (int j = 0; j < BattleGame.grid[i].length; j++) {
                BattleGame.gridLock.lock();
                try {
                    if (BattleGame.grid[i][j] == CellState.CELL_UNCLICKED.toInt()) {
                        BattleGame.grid[i][j] = CellState.CELL_CLICKED.toInt();
                        System.out.println("Player " + playerId + " destroyed the ship at (" + i + "," + j + ")");
                    } else {
                        System.out.println("Player " + playerId + " checked a destroyed ship at (" + i + "," + j + ")");
                    }
                } finally {
                    BattleGame.gridLock.unlock();
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
