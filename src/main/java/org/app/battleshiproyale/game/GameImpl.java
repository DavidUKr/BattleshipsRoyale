package org.app.battleshiproyale.game;

import org.app.battleshiproyale.BattleshipRoyaleApplication;
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

    private final BattleshipRoyaleApplication battleshipRoyaleApplication;
    private ArrayList<Player> players= new ArrayList<>();
    private final int MAX_PLAYERS =2;
    private final int PLAYER_GRID_SIZE =10;
    private BattleGrid battleGrid;

    public GameImpl(BattleshipRoyaleApplication battleshipRoyaleApplication){
        this.battleGrid = new BattleGrid();
        this.battleshipRoyaleApplication = battleshipRoyaleApplication;
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
                    if(playerGrid[point.getX()][point.getY()].cellType!=GridCell.CellType.UNDISCOVERED_SHIP_TEAM_1 && playerGrid[point.getX()][point.getY()].cellType!=GridCell.CellType.UNDISCOVERED_SHIP_TEAM_2)
                        if (playerId==players.get(0).getId()) {
                            playerGrid[point.getX()][point.getY()] = new GridCell(GridCell.CellType.UNDISCOVERED_SHIP_TEAM_1, ship.getShip_id());
                        }
                        else {
                            playerGrid[point.getX()][point.getY()] = new GridCell(GridCell.CellType.UNDISCOVERED_SHIP_TEAM_2, ship.getShip_id());
                        }
                }
            }
            this.battleGrid.placePlayerGridOnMain(playerGrid,
                    players.stream().filter(player -> player.getId()==playerId).findFirst().get().getShips(),
                    players.stream().filter(player -> player.getId()==playerId).findFirst().get().getShips()
            );
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
        return new GameState(battleGrid.getMainGrid(), players, false);
    }

    @Override
    public GameState resetBoard() {
        return null;
    }
}

