package org.app.battleshiproyale.game;

import org.app.battleshiproyale.game.game_elements.BattleGrid;
import org.app.battleshiproyale.game.game_elements.GridCell;
import org.app.battleshiproyale.game.game_elements.ships.BaseShip;
import org.app.battleshiproyale.model.Player;
import org.app.battleshiproyale.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameImpl implements Game{

    private ArrayList<Player> players= new ArrayList<>();;
    private final int MAX_PLAYERS =2;
    private final int PLAYER_GRID_SIZE =10;
    private BattleGrid battleGrid;

    public GameImpl(){
        this.battleGrid = new BattleGrid();
    }

    public void startGame() {
        //TODO STAMINA JobRnr. ...
    }

    public static void main(String[] args) {
        System.out.println("Running Game");

        BattleGrid battleGrid = new BattleGrid();

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
    public boolean placePlayerShips(String playerId, PlayerMap playerMap) {
        if (getJoinedPlayersIds().contains(playerId)){
            GridCell[][] playerGrid=new GridCell[10][10];
            for(BaseShip ship : playerMap.getShips()){
                for ( Point point : ship.getCoordinates()){
                    if (playerId==players.get(0).getId())
                        playerGrid[point.getX()][point.getY()]=new GridCell(4);
                    else
                        playerGrid[point.getX()][point.getY()]=new GridCell(5);
                }
            }
            this.battleGrid.placePlayerGridOnMain(playerGrid);
            return true;
        }

        return false;
    }

    @Override
    public void setPlayerReady(String playerId) {
        for (Player player : players) {
            if (player.getId().equals(playerId)) {
                player.setReadyForBattle(true);
            }
        }
    }

    @Override
    public List<String> getReadyPlayersIds() {
        return players.stream().map(Player::getId).toList();
    }

    @Override
    public void resetSession(){
        players.clear();
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
    public GameState getGameState() {
        return null;
    }

    @Override
    public GameState resetBoard() {
        return null;
    }
}

