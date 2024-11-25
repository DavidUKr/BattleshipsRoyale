package org.app.battleshiproyale.game;

import org.app.battleshiproyale.game.game_elements.BattleGrid;
import org.app.battleshiproyale.game.game_elements.BattlegridRenderer;
import org.app.battleshiproyale.model.Player;
import org.app.battleshiproyale.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameImpl implements Game{

    ArrayList<Player> players;
    private final int MAX_PLAYERS =2;

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

        Thread renderer = new Thread(new BattlegridRenderer(battleGrid));

        printMap(battleGrid);

//        startGame(playerThread1, playerThread2);

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

    @Override
    public boolean joinPlayer(String playerId) {
        if (players.size() <= MAX_PLAYERS){
            players.add(new Player(playerId));
            return true;
        }
        return false;
    }

    @Override
    public List<String> getJoinedPlayersIds() {
        return players.stream().map(Player::getId).toList();
    }

    @Override
    public void placePlayerShips(String playerId, PlayerMapDTO playerMapDTO) {

    }

    @Override
    public void setPlayerReady(String playerId) {

    }

    @Override
    public List<String> getReadyPlayersIds() {
        return new ArrayList<String>();
    }

    @Override
    public void resetSession(){
        //TODO remove players
    }

    @Override
    public HitResult hit(String playerId, HitDTO hitDTO) {
        return new HitResult();
    }

    @Override
    public boolean usePerk(String playerId, PerkDTO perkDTO) {
        return false;
    }

    @Override
    public int getPlayerStamina(String player_id) {
        return 0;
    }

    @Override
    public GameState resetBoard() {
        return null;
    }
}

