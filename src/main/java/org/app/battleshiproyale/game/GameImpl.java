package org.app.battleshiproyale.game;

import org.app.battleshiproyale.game.game_elements.BattleGrid;
import org.app.battleshiproyale.model.Player;
import org.app.battleshiproyale.game.game_elements.BattlegridRenderer;
import org.app.battleshiproyale.game.game_elements.GridCell;
import org.app.battleshiproyale.model.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameImpl implements Game{

    private ArrayList<Player> players= new ArrayList<>();;
    private final int MAX_PLAYERS =2;
    public static BattleGrid battleGrid;

    public void startGame() {
        //TODO STAMINA JobRnr. ...
    }

    public static void main(String[] args) {
        System.out.println("Running Game");

        battleGrid = new BattleGrid();

//        printMap(battleGrid);

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
    public void placePlayerShips(String playerId, PlayerMap playerMap) {

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
        players.clear();
    }

    public GameState resetBoard() {
        GridCell[][] mainGrid = battleGrid.getMainGrid();
        boolean isEnd = battleGrid.isFinished();
//        return new GameState(mainGrid, isEnd);
        return new GameState();
    }

    @Override
    public HitResult hit(String playerId, HitDTO hitDTO) {
        int x = hitDTO.getX();
        int y = hitDTO.getY();
        int width = battleGrid.getMAIN_GRID_SIZE();
        int length = battleGrid.getMAIN_GRID_SIZE();
        GridCell[][] grid = battleGrid.getMainGrid();

        boolean hitSuccess = battleGrid.hit(x, y, Integer.parseInt(playerId), grid, width, length);

        if (hitSuccess) {
            GridCell cell = battleGrid.getMainGrid()[x][y];
            return new HitResult(cell.cellType);

        }
        return new HitResult(GridCell.CellType.INVALID);
    }

    @Override
    public boolean usePerk(String playerId, PerkDTO perkDTO) {
        return false;
    }

    @Override
    public int getPlayerStamina(String player_id) {
        return 0;
    }

}

