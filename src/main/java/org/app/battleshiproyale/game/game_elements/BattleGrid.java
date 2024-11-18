package org.app.battleshiproyale.game.game_elements;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;
import org.app.battleshiproyale.game.game_elements.ships.BaseShip;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BattleGrid {

    public Lock gridLock = new ReentrantLock();

    @Getter
    private boolean isFinished=false;
    @Getter
    private int winningTeamId=-1;
    //@Getter
    //private final int x_size;
    //@Getter
    //private final int y_size;
    @Getter@Setter
    private int x_divider;

    private final int MAIN_GRID_SIZE = 100;
    private final int PLAYER_GRID_SIZE = 10;

    // Main 100x100 grid
    private final GridCell[][] mainGrid;

    // 10x10 grids for each player
    private final GridCell[][] player1Grid;
    private final GridCell[][] player2Grid;


    //public final GridCell[][] grid;
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

    private ArrayList<BaseShip> ships=new ArrayList<>();

    public BattleGrid() {
        mainGrid = new GridCell[MAIN_GRID_SIZE][MAIN_GRID_SIZE];
        player1Grid = new GridCell[PLAYER_GRID_SIZE][PLAYER_GRID_SIZE];
        player2Grid = new GridCell[PLAYER_GRID_SIZE][PLAYER_GRID_SIZE];

        // Initialize the main grid with default values
        for (int i = 0; i < MAIN_GRID_SIZE; i++) {
            for (int j = 0; j < MAIN_GRID_SIZE; j++) {
                mainGrid[i][j] = new GridCell(3);  // 3 for undiscovered empty
            }
        }

        // Initialize the player grids similarly
        for (int i = 0; i < PLAYER_GRID_SIZE; i++) {
            for (int j = 0; j < PLAYER_GRID_SIZE; j++) {
                player1Grid[i][j] = new GridCell(3);
                player2Grid[i][j] = new GridCell(3);
            }
        }
    }

    public void placePlayerGridOnMain(GridCell[][] playerGrid) {
        int startX = (int) (Math.random() * (MAIN_GRID_SIZE - PLAYER_GRID_SIZE));
        int startY = (int) (Math.random() * (MAIN_GRID_SIZE - PLAYER_GRID_SIZE));

        // Copy the player grid to the main grid
        for (int i = 0; i < PLAYER_GRID_SIZE; i++) {
            for (int j = 0; j < PLAYER_GRID_SIZE; j++) {
                mainGrid[startX + i][startY + j] = playerGrid[i][j];
            }
        }
    }


    public boolean place_ship(BaseShip ship, int x, int y, int orientation, GridCell[][] grid, int gridWidth, int gridHeight) {
        int shipLength = ship.getLength();

        // Determine ship boundaries
        if (orientation == 0) { // Horizontal
            if (x + shipLength > gridWidth) {
                System.out.println("Ship placement out of bounds (horizontal)");
                return false;
            }
        } else if (orientation == 1) { // Vertical
            if (y + shipLength > gridHeight) {
                System.out.println("Ship placement out of bounds (vertical)");
                return false;
            }
        } else {
            System.out.println("Invalid orientation");
            return false;
        }

        // Check for collisions
        for (int i = 0; i < shipLength; i++) {
            int checkX = (orientation == 0) ? x + i : x; // Horizontal or vertical
            int checkY = (orientation == 1) ? y + i : y;

            if (grid[checkX][checkY].cellType != 3) {
                System.out.println("Collision detected at: (" + checkX + ", " + checkY + ")");
                return false;
            }
        }

        // Place the ship
        int cellType = (ship.getTeam_id() == 0) ? 4 : 5; // Team 1 or Team 2
        for (int i = 0; i < shipLength; i++) {
            int placeX = (orientation == 0) ? x + i : x;
            int placeY = (orientation == 1) ? y + i : y;

            grid[placeX][placeY].cellType = cellType;
        }

        ships.add(ship);

        System.out.println("Ship placed successfully at: (" + x + ", " + y + ")");

        // If the ship is placed successfully:
        List<int[]> shipCoordinates = new ArrayList<>();
        if (orientation == 0) { // Horizontal
            for (int i = 0; i < ship.getLength(); i++) {
                shipCoordinates.add(new int[]{x + i, y});
            }
        } else if (orientation == 1) { // Vertical
            for (int i = 0; i < ship.getLength(); i++) {
                shipCoordinates.add(new int[]{x, y - i});
            }
        }

        // Set the ship's coordinates and grid reference
        ship.setCoordinates(shipCoordinates);
        //ship.setGrid(this.grid); // Assuming 'grid' is a field in `BattleGrid`

        return true;
    }


    public void generatePerks(int numPerks, int perkType) {
        int perksPlaced = 0;
        while (perksPlaced < numPerks) {
            int x = (int) (Math.random() * MAIN_GRID_SIZE);
            int y = (int) (Math.random() * MAIN_GRID_SIZE);

            if (mainGrid[x][y].cellType == 3) {
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

        // Identify cell type and process hit
        switch (grid[x][y].cellType) {
            case 0: // Already empty
                System.out.println("Already hit an empty cell at (" + x + ", " + y + ")");
                return false;

            case 1:
            case 2:
                System.out.println("Already hit a ship at (" + x + ", " + y + ")");
                return false;

            case 3: // Undiscovered empty
                grid[x][y].cellType = 0; // Mark as discovered empty
                System.out.println("Missed! Hit empty cell at (" + x + ", " + y + ")");
                return true;

            case 4: // Undiscovered ship (Team 1)
                grid[x][y].cellType = 1; // Mark as discovered ship
                System.out.println("Hit a ship from Team 1 at (" + x + ", " + y + ")");
                if (check_finish(0) == 1) { // Check if all Team 1 ships are hit
                    isFinished = true;
                    winningTeamId = 1; // Team 2 wins
                    System.out.println("Team 2 wins!");
                }
                return true;

            case 5: // Undiscovered ship (Team 2)
                grid[x][y].cellType = 2; // Mark as discovered ship
                System.out.println("Hit a ship from Team 2 at (" + x + ", " + y + ")");
                if (check_finish(1) == 0) { // Check if all Team 2 ships are hit
                    isFinished = true;
                    winningTeamId = 0; // Team 1 wins
                    System.out.println("Team 1 wins!");
                }
                return true;

            case 6: // Perk type 1
                grid[x][y].cellType = 0; // Mark as discovered empty
                System.out.println("Discovered a perk (Type 1) at (" + x + ", " + y + ")");
                return true;

            case 7: // Perk type 2
                grid[x][y].cellType = 0; // Mark as discovered empty
                System.out.println("Discovered a perk (Type 2) at (" + x + ", " + y + ")");
                return true;

            default: // Invalid cell type
                System.out.println("Unknown cell type at (" + x + ", " + y + ")");
                return false;
        }
    }



    public void printPlayerGrid(GridCell[][] playerGrid, int team_id) {
        StringBuilder output = new StringBuilder();
        output.append("Team ").append(team_id + 1).append("'s Grid:\n");
        output.append("----------------------\n");

        for (int i = 0; i < PLAYER_GRID_SIZE; i++) {
            output.append("| ");
            for (int j = 0; j < PLAYER_GRID_SIZE; j++) {
                int cellType = playerGrid[i][j].cellType;

                switch (cellType) {
                    case 0: // Discovered empty
                        output.append(" ");
                        break;
                    case 1: // Discovered ship (Team 1)
                    case 2: // Discovered ship (Team 2)
                        output.append("X");
                        break;
                    case 3: // Undiscovered empty
                        output.append(".");
                        break;
                    case 4: // Undiscovered ship (Team 1)
                        output.append(team_id == 0 ? "$" : "-"); // Team 1 sees their own ships
                        break;
                    case 5: // Undiscovered ship (Team 2)
                        output.append(team_id == 1 ? "$" : "-"); // Team 2 sees their own ships
                        break;
                    default:
                        output.append("?"); // Unknown cell type
                }
                output.append(" ");
            }
            output.append("|\n");
        }
        output.append("----------------------");
        System.out.println(output);
    }

    public void printMainGrid() {
        StringBuilder output = new StringBuilder();
        output.append("Main 100x100 Grid (Admin View):\n");
        output.append("----------------------\n");

        for (int i = 0; i < MAIN_GRID_SIZE; i++) {
            for (int j = 0; j < MAIN_GRID_SIZE; j++) {
                int cellType = mainGrid[i][j].cellType;

                switch (cellType) {
                    case 0:
                        output.append(" ");
                        break;
                    case 1:
                    case 2:
                        output.append("X");
                        break;
                    case 3:
                        output.append(".");
                        break;
                    case 4:
                    case 5:
                        output.append("$");
                        break;
                    case 6:
                    case 7:
                        output.append("P");
                        break;
                    default:
                        output.append("?");
                }
                output.append(" ");
            }
            output.append("\n");
        }
        System.out.println(output);
    }


    //TODO implement looking in ships array
    private int check_finish(int team_id) {
        boolean allTeam1ShipsDestroyed = true;
        boolean allTeam2ShipsDestroyed = true;

        for (BaseShip ship : ships) {
            if (ship.getTeam_id() == 0 && !ship.isDestroyed()) {
                allTeam1ShipsDestroyed = false;
            }
            if (ship.getTeam_id() == 1 && !ship.isDestroyed()) {
                allTeam2ShipsDestroyed = false;
            }
        }

        if (allTeam1ShipsDestroyed && team_id == 0) {
            return 1; // Team 2 wins
        }

        if (allTeam2ShipsDestroyed && team_id == 1) {
            return 0; // Team 1 wins
        }

        return -1;
    }

}
