package org.app.battleshiproyale.game;

import org.app.battleshiproyale.BattleshipRoyaleApplication;
import org.app.battleshiproyale.game.game_elements.BattleGrid;
import org.app.battleshiproyale.game.game_elements.GridCell;
import org.app.battleshiproyale.game.game_elements.ships.BaseShip;
import org.app.battleshiproyale.model.Player;
import org.app.battleshiproyale.model.*;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Component
public class GameImpl implements Game {

    private final BattleshipRoyaleApplication battleshipRoyaleApplication;
    private ArrayList<Player> players= new ArrayList<>();
    private final int MAX_PLAYERS =2;
    private final int PLAYER_GRID_SIZE =10;
    private BattleGrid battleGrid;
    private JobScheduler jobScheduler;

    public GameImpl(BattleshipRoyaleApplication battleshipRoyaleApplication, JobScheduler jobScheduler){
        this.battleGrid = new BattleGrid();
        this.battleshipRoyaleApplication = battleshipRoyaleApplication;
        this.jobScheduler = jobScheduler;
    }

    @Override
    public void startGame() {
        System.out.println("Game started. Scheduling stamina regeneration job.");

        // Regenerate stamina every 10 seconds
        jobScheduler.scheduleRecurrently("stamina-regeneration", Duration.ofSeconds(1), this::regenerateStamina);
    }

    private void regenerateStamina() {
        for (Player player : players) {
            if (player.getStamina() < 100) {
                player.increaseStamina(10);
                System.out.printf("Player %s stamina increased to %d.%n", player.getId(), player.getStamina());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Running Game");

        BattleGrid battleGrid = new BattleGrid();

//        printMap(battleGrid);

        System.out.println("Winning team: " + (battleGrid.getWinningTeamId() + 1));
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
            //init grid
            for(int i=0;i<10;i++){
                for(int j=0;j<10;j++){
                    playerGrid[i][j]=new GridCell(GridCell.CellType.UNDISCOVERED_EMPTY);
                }
            }
            //place ships
            for(BaseShip ship : playerMap.getShips()){
                for ( Point point : ship.getCoordinates()){
                    if (playerId.equals(players.get(0).getId())) {
                        playerGrid[point.getX()][point.getY()] = new GridCell(GridCell.CellType.UNDISCOVERED_SHIP_TEAM_1, ship.getShip_id());
                    }
                    else {
                        playerGrid[point.getX()][point.getY()] = new GridCell(GridCell.CellType.UNDISCOVERED_SHIP_TEAM_2, ship.getShip_id());
                    }
                }
            }
            if (playerId.equals(players.get(0).getId())) {
                players.get(0).setShips(playerMap.getShips());
                this.battleGrid.placePlayerGridOnMain(playerGrid,
                        players.stream().filter(player -> player.getId().equals(playerId)).findFirst().get().getShips(),
                        0
                );
            }

            else {
                players.get(1).setShips(playerMap.getShips());
                this.battleGrid.placePlayerGridOnMain(playerGrid,
                        players.stream().filter(player -> player.getId().equals(playerId)).findFirst().get().getShips(),
                        1
                );
            }
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
    public ArrayList<Player> getReadyPlayersIds() {
        return players;
    }

    @Override
    public void resetSession(){
        players.clear();
        battleGrid.resetGrid();
    }

    @Override
    public HitResult hit(String playerId, HitDTO hitDTO) {
        Player player = players.stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElse(null);

        if (player == null) {
            System.out.println("Player not found: " + playerId);
            return new HitResult(GridCell.CellType.INVALID, "Player not found.");
        }

        int hitResInt = battleGrid.hit(hitDTO.getX(), hitDTO.getY(), player);

        switch (hitResInt){

            case 0: return new HitResult(GridCell.CellType.DISCOVERED_EMPTY, "Missed!");

            case 1: return new HitResult(GridCell.CellType.DISCOVERED_EMPTY, "Already hit ship!");

            case 2: return new HitResult(GridCell.CellType.HIT_ENEMY_SHIP, "Hit enemy ship!");

            case -1: return new HitResult(GridCell.CellType.DISCOVERED_EMPTY, "Wrong input");

            case -2: return new HitResult(GridCell.CellType.DISCOVERED_EMPTY, "Not enough stamina");

            default: return new HitResult(GridCell.CellType.DISCOVERED_EMPTY, "Error");
        }
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

        return new GameState(battleGrid.getMainGrid(), players, battleGrid.isFinished(), battleGrid.getMAIN_GRID_SIZE_X(), battleGrid.getMAIN_GRID_SIZE_Y());
    }

    @Override
    public GameState resetBoard() {
        return null;
    }
}

