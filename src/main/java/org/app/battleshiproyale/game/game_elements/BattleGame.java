package org.app.battleshiproyale.game.game_elements;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BattleGame {

    private static final int GRID_SIZE = 5;
    public static final int[][] grid = new int[GRID_SIZE][GRID_SIZE];

    public static final Lock gridLock = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = 0;
            }
        }

        Thread player1 = new Thread(new Player(1));
        Thread player2 = new Thread(new Player(2));

        player1.start();
        player2.start();

        try {
            player1.join();
            player2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
