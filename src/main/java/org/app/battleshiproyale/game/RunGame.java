package org.app.battleshiproyale.game;

import org.app.battleshiproyale.game.game_elements.BattleGrid;
import org.app.battleshiproyale.game.game_elements.Player;

public class RunGame {

    public static Player player1;
    public static Player player2;

    public static void StartGame(Thread playerThread1, Thread playerThread2){
        playerThread1.start();
        playerThread2.start();

        try {
            playerThread1.join();
            playerThread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Running Game");

        BattleGrid battleGrid = new BattleGrid();

        player1 = new Player(0, battleGrid);
        player2 = new Player(1, battleGrid);

        Thread playerThread1 = new Thread(player1);
        Thread playerThread2 = new Thread(player2);

        printMap(battleGrid);
        StartGame(playerThread1, playerThread2);
        printMap(battleGrid);

    }

    private static void printMap(BattleGrid battleGrid) {
        battleGrid.printGrid(0);
        battleGrid.printGrid(1);
    }

}
