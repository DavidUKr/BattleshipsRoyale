package org.app.battleshiproyale.game;

import org.app.battleshiproyale.game.game_elements.BattleGrid;
import org.app.battleshiproyale.game.game_elements.BattlegridRenderer;
import org.app.battleshiproyale.game.game_elements.Player;

public class Game {

    public static Player player1;
    public static Player player2;

    public static void startGame(Thread playerThread1, Thread playerThread2) {
        playerThread1.start();
        playerThread2.start();

        try {
            playerThread1.join();
            playerThread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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

        Thread renderer = new Thread(new BattlegridRenderer(battleGrid));

        printMap(battleGrid);

        startGame(playerThread1, playerThread2);

        printMap(battleGrid);

        System.out.println("Winning team: " + (battleGrid.getWinningTeamId() + 1));
    }

    private static void printMap(BattleGrid battleGrid) {
        System.out.println("\nPlayer 1's Grid:");
        battleGrid.printPlayerGrid(battleGrid.getPlayer1Grid(),0);

        System.out.println("\nPlayer 2's Grid:");
        battleGrid.printPlayerGrid(battleGrid.getPlayer2Grid(),1);

        System.out.println("\nMain Grid:");
        battleGrid.printMainGrid();
    }
}

