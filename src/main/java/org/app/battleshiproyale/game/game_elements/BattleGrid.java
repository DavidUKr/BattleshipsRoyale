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
    //@Getter
    //private final int x_size;
    //@Getter
    //private final int y_size;
    @Getter @Setter
    private String player1_id;
    @Getter @Setter
    private String player2_id;
    @Getter@Setter
    private int x_divider;
    @Getter
    private final int MAIN_GRID_SIZE = 100;
    @Getter
    private final int PLAYER_GRID_SIZE = 10;
    @Getter
    private final GridCell[][] mainGrid;
    @Getter
    private final GridCell[][] player1Grid;
    @Getter
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
    public int team1ShipCells = 0;
    public int team2ShipCells = 0;
    public int team1Hits = 0;
    public int team2Hits = 0;

    /*public BattleGrid() {
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
    } */

    // This is a hard-coded grid for simulation purposes
    public BattleGrid() {
        mainGrid = new GridCell[MAIN_GRID_SIZE][MAIN_GRID_SIZE];
        player1Grid = new GridCell[PLAYER_GRID_SIZE][PLAYER_GRID_SIZE];
        player2Grid = new GridCell[PLAYER_GRID_SIZE][PLAYER_GRID_SIZE];

        // Initialize the main grid with default values
        for (int i = 0; i < MAIN_GRID_SIZE; i++) {
            for (int j = 0; j < MAIN_GRID_SIZE; j++) {
                mainGrid[i][j] = new GridCell(3); // 3 for undiscovered empty
            }
        }

        // Initialize the player grids with default values
        for (int i = 0; i < PLAYER_GRID_SIZE; i++) {
            for (int j = 0; j < PLAYER_GRID_SIZE; j++) {
                player1Grid[i][j] = new GridCell(3); // 3 for undiscovered empty
                player2Grid[i][j] = new GridCell(3); // 3 for undiscovered empty
            }
        }

        // Hard-code Player 1's grid
        // Place a ship horizontally starting at (0,0) of length 4
        for (int i = 0; i < 4; i++) {
            player1Grid[0][i] = new GridCell(4); // 4 for undiscovered ship (team 1)
            team1ShipCells++;

        }

        // Place another ship vertically starting at (5,5) of length 3
        for (int i = 0; i < 3; i++) {
            player1Grid[5 + i][5] = new GridCell(4); // 4 for undiscovered ship (team 1)
            team1ShipCells++;
        }

        // Hard-code Player 2's grid
        // Place a ship horizontally starting at (2,3) of length 5
        for (int i = 0; i < 5; i++) {
            player2Grid[2][3 + i] = new GridCell(5); // 5 for undiscovered ship (team 2)
            team2ShipCells++;
        }

        // Place another ship vertically starting at (7,1) of length 2
        for (int i = 0; i < 2; i++) {
            player2Grid[7 + i][1] = new GridCell(5); // 5 for undiscovered ship (team 2)
            team2ShipCells++;
        }

        // Hard-code the main grid (optional, if you want to see player grids on the main grid)
        // Copy Player 1's grid onto the main grid at a fixed location
        int startX1 = 10; // Top-left corner for Player 1's grid on the main grid
        int startY1 = 10;
        for (int i = 0; i < PLAYER_GRID_SIZE; i++) {
            for (int j = 0; j < PLAYER_GRID_SIZE; j++) {
                mainGrid[startX1 + i][startY1 + j] = player1Grid[i][j];
            }
        }

        // Copy Player 2's grid onto the main grid at a fixed location
        int startX2 = 50; // Top-left corner for Player 2's grid on the main grid
        int startY2 = 50;
        for (int i = 0; i < PLAYER_GRID_SIZE; i++) {
            for (int j = 0; j < PLAYER_GRID_SIZE; j++) {
                mainGrid[startX2 + i][startY2 + j] = player2Grid[i][j];
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
        int cellType = (ship.getPlayer_id() == player1_id) ? 4 : 5; // Team 1 or Team 2
        if (ship.getPlayer_id() == player1_id) {
            team1ShipCells++; // Increment Team 1's total ship cells
        } else {
            team2ShipCells++; // Increment Team 2's total ship cells
        }
        for (int i = 0; i < shipLength; i++) {
            int placeX = (orientation == 0) ? x + i : x;
            int placeY = (orientation == 1) ? y + i : y;

            grid[placeX][placeY].cellType = cellType;
        }

        ships.add(ship);

        System.out.println("Ship placed successfully at: (" + x + ", " + y + ")");

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
//        ship.setCoordinates(shipCoordinates);
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
            case 2: // Already hit a ship
                System.out.println("Already hit a ship at (" + x + ", " + y + ")");
                return false;

            case 3: // Undiscovered empty
                grid[x][y].cellType = 0; // Mark as discovered empty
                System.out.println("Missed! Hit empty cell at (" + x + ", " + y + ")");
                return true;

            case 4: // Undiscovered ship (Team 1)
                grid[x][y].cellType = 1; // Mark as discovered ship
                team2Hits++; // Increment hits by Team 2 on Team 1
                System.out.println("team2Hits: " + team2Hits + ", team1ShipCells: " + team1ShipCells);
                System.out.println("Hit a ship from Team 1 at (" + x + ", " + y + ")");
                if (team2Hits == team1ShipCells) { // Check if Team 2 has destroyed all of Team 1's ships
                    isFinished = true;
                    winningTeamId = 1; // Team 2 wins
                    System.out.println("Team 2 wins!");
                }
                return true;

            case 5: // Undiscovered ship (Team 2)
                grid[x][y].cellType = 2; // Mark as discovered ship
                team1Hits++; // Increment hits by Team 1 on Team 2
                System.out.println("team1Hits: " + team1Hits + ", team2ShipCells: " + team2ShipCells);
                System.out.println("Hit a ship from Team 2 at (" + x + ", " + y + ")");
                if (team1Hits == team2ShipCells) { // Check if Team 1 has destroyed all of Team 2's ships
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
    public String check_finish(String player_id) {
        for (BaseShip ship : ships) {
            if (ship.getPlayer_id().equals(player_id) && !ship.isDestroyed()) {
                return player_id; // Team still has undestroyed ships
            }
        }
        return (player_id.equals(player1_id)) ? player2_id : player1_id; // All ships of the team are destroyed
    }

}
