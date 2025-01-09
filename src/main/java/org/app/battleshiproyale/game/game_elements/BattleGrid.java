package org.app.battleshiproyale.game.game_elements;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;
import org.app.battleshiproyale.game.game_elements.ships.BaseShip;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class BattleGrid {

    public Lock gridLock = new ReentrantLock();

    @Getter
    private boolean isFinished=false;
    @Getter
    private int winningTeamId=-1;
    @Getter
    private final int MAIN_GRID_SIZE_X = 33;
    @Getter
    private final int MAIN_GRID_SIZE_Y = 33;
    @Getter
    private final int PLAYER_GRID_SIZE = 10;
    @Getter
    private final GridCell[][] mainGrid;

    private int other_player_map_x=-1;
    private int other_player_map_y=-1;

    /* grid code
    *  0 - discovered empty
    *  1 - discovered ship team 1
    *  2 - discovered ship team 2
    *  3 - undiscovered empty
    *  4 - undiscovered ship team 1
    *  5 - undiscovered ship team 2
    *  6 - undiscovered perk 1
    *  7 - undiscovered perk 2
    */
    private ArrayList<BaseShip> shipsPlayer1=new ArrayList<>();
    private ArrayList<BaseShip> shipsPlayer2=new ArrayList<>();

    public BattleGrid() {
        mainGrid = new GridCell[MAIN_GRID_SIZE_X][MAIN_GRID_SIZE_Y];

        // Initialize the main grid with default values
        for (int i = 0; i < MAIN_GRID_SIZE_X; i++) {
            for (int j = 0; j < MAIN_GRID_SIZE_Y; j++) {
                mainGrid[i][j] = new GridCell(GridCell.CellType.UNDISCOVERED_EMPTY);
            }
        }
    }

    public void placePlayerGridOnMain(GridCell[][] playerGrid, List<BaseShip> shipsPlayer) {
        boolean isPlacedGood = false;
        int startX = -1;
        int startY = -1;

        while (!isPlacedGood) {
            startX = (int) (Math.random() * (MAIN_GRID_SIZE_X - PLAYER_GRID_SIZE));
            startY = (int) (Math.random() * (MAIN_GRID_SIZE_Y - PLAYER_GRID_SIZE));

            if (other_player_map_x == -1) {
                other_player_map_x = startX;
                other_player_map_y = startY;
                isPlacedGood = true;
            } else {
                if (startX < other_player_map_x - 10 || startX > other_player_map_x + 10) {
                    isPlacedGood = true;
                } else if (startY < other_player_map_y - 10 || startY > other_player_map_y + 10) {
                    isPlacedGood = true;
                }
            }
        }

        System.out.println("Placing player grid " + startX + " " + startY);

        // Copy the player grid to the main grid
        for (int i = 0; i < PLAYER_GRID_SIZE; i++) {
            for (int j = 0; j < PLAYER_GRID_SIZE; j++) {
                mainGrid[startX + i][startY + j] = playerGrid[i][j];
            }
        }
        this.shipsPlayer1.addAll(shipsPlayer);
    }


    public void generatePerks(int numPerks, GridCell.CellType perkType) {
        int perksPlaced = 0;
        while (perksPlaced < numPerks) {
            int x = (int) (Math.random() * MAIN_GRID_SIZE_X);
            int y = (int) (Math.random() * MAIN_GRID_SIZE_Y);

            if (mainGrid[x][y].cellType == GridCell.CellType.UNDISCOVERED_EMPTY) {
                mainGrid[x][y] = new GridCell(perkType);
                perksPlaced++;
            }
        }
    }

    public boolean hit(int x, int y, int team_id, GridCell[][] grid, int gridWidth, int gridHeight) {
        // Validate coordinates
        if (x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) {
            System.out.println("Out of bounds: (" + x + ", " + y + ")");
            return false;
        }

        //TODO identify ship from ships arrays based on ship_id and call ship.apply_damage()
        // Identify cell type and process hit
        switch (grid[x][y].cellType) {
            case DISCOVERED_EMPTY:
                System.out.println("Already hit an empty cell at (" + x + ", " + y + ")");
                return false;

            case DISCOVERED_SHIP_TEAM_1:
            case DISCOVERED_SHIP_TEAM_2:
                System.out.println("Already hit a ship at (" + x + ", " + y + ")");
                return false;

            case UNDISCOVERED_EMPTY:
                grid[x][y].cellType = GridCell.CellType.DISCOVERED_EMPTY;
                System.out.println("Missed! Hit empty cell at (" + x + ", " + y + ")");
                return true;

            case UNDISCOVERED_SHIP_TEAM_1: // Mark as discovered ship
//                team2Hits++; // Increment hits by Team 2 on Team 1
//                System.out.println("team2Hits: " + team2Hits + ", team1ShipCells: " + team1ShipCells);
//                System.out.println("Hit a ship from Team 1 at (" + x + ", " + y + ")");
//                if (team2Hits == team1ShipCells) { // Check if Team 2 has destroyed all of Team 1's ships
//                    isFinished = true;
//                    winningTeamId = 1; // Team 2 wins
//                    System.out.println("Team 2 wins!");
//                }
                //TODO check is finished for player1
                return true;

            case UNDISCOVERED_SHIP_TEAM_2: // Mark as discovered ship
//                team1Hits++; // Increment hits by Team 1 on Team 2
//                System.out.println("team1Hits: " + team1Hits + ", team2ShipCells: " + team2ShipCells);
//                System.out.println("Hit a ship from Team 2 at (" + x + ", " + y + ")");
//                if (team1Hits == team2ShipCells) { // Check if Team 1 has destroyed all of Team 2's ships
//                    isFinished = true;
//                    winningTeamId = 0; // Team 1 wins
//                    System.out.println("Team 1 wins!");
//                }
                //TODO check is finished for player2
                return true;

            case UNDISCOVERED_PERK_1:
                grid[x][y].cellType = GridCell.CellType.DISCOVERED_EMPTY;
                System.out.println("Discovered a perk (Type 1) at (" + x + ", " + y + ")");
                return true;

            case UNDISCOVERED_PERK_2:
                grid[x][y].cellType = GridCell.CellType.DISCOVERED_EMPTY;
                System.out.println("Discovered a perk (Type 2) at (" + x + ", " + y + ")");
                return true;

            default:
                System.out.println("Unknown cell type at (" + x + ", " + y + ")");
                return false;
        }
    }

    //TODO implement looking in ships array
    public boolean check_finish() {
//        for (BaseShip ship : ships) {
//            if (ship.getPlayer_id().equals(player_id) && !ship.isDestroyed()) {
//                return player_id; // Team still has undestroyed ships
//            }
//        }
//        return (player_id.equals(player1_id)) ? player2_id : player1_id; // All ships of the team are destroyed
        //TODO check each boat of the said player
        return false;
    }

}
