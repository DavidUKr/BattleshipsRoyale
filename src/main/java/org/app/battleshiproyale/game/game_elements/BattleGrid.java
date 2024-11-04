package org.app.battleshiproyale.game.game_elements;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.Setter;
import org.app.battleshiproyale.game.game_elements.ships.BaseShip;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BattleGrid {

    public Lock gridLock = new ReentrantLock();

    @Getter
    private boolean isFinished=false;
    @Getter
    private int winningTeamId=-1;
    @Getter
    private final int x_size;
    @Getter
    private final int y_size;
    @Getter@Setter
    private int x_divider;

    public final GridCell[][] grid;
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
        this.x_size = 10;
        this.y_size = 10;
        grid = new GridCell[x_size][y_size];

        // Initialize each cell in the grid with a specific cellType
        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                int cellType;
                // Initial grid setup based on your provided layout
                if ((i == 1 && j == 1) || (i == 1 && j == 7)) cellType = 7;
                else if (i == 6 && j >= 6 && j <= 8) cellType = 5;
                else if (j == 2 && i >= 2 && i <= 4) cellType = 4;
                else if (i == 2 && j == 6) cellType = 6;
                else cellType = 3; // default to undiscovered empty
                grid[i][j] = new GridCell(cellType);
            }
        }
    }

    public boolean place_ship(BaseShip ship, int x, int y, int orientation) {
        int left, right, up = y_size, down = 0;
        int cellType; // This will store the correct cell type for each team

        if (ship.getTeam_id() == 0) {
            left = 0;
            right = x_divider;
            cellType = 4; // Set to team 1's undiscovered ship type
        } else if (ship.getTeam_id() == 1) {
            left = x_divider + 1;
            right = x_size;
            cellType = 5; // Set to team 2's undiscovered ship type
        } else {
            System.out.println("Unknown team id");
            return false;
        }

        if (x < right && x > left && y < up && y > down) {
            if (orientation == 0 && x + ship.getLength() - 1 < right) {
                ships.add(ship);
                for (int i = 0; i < ship.getLength(); i++) {
                    grid[x + i][y].cellType = cellType; // Assign the correct cell type
                }
                return true;
            } else if (orientation == 1 && y - ship.getLength() + 1 > 0) {
                ships.add(ship);
                for (int i = 0; i < ship.getLength(); i++) {
                    grid[x][y - i].cellType = cellType; // Assign the correct cell type
                }
                return true;
            } else {
                System.out.println("Ship " + ship + " did not fit because of length and orientation");
                return false;
            }
        }
        return false;
    }



    public boolean hit(int x, int y, int team_id) {
        if (team_id == 0 || team_id == 1) {
            System.out.print("Player " + (team_id + 1) + ":");
        } else System.out.println("Player " + team_id + ": unknown team id");

        if (x < x_size && x >= 0 && y < y_size && y >= 0) {
            switch (grid[x][y].cellType) {
                case 0:
                    System.out.println("Already empty");
                    return false;
                case 1, 2:
                    System.out.println("Already hit");
                    return false;
                case 3:
                    grid[x][y].cellType = 0;
                    System.out.println("Hit empty");
                    return true;
                case 4:
                    grid[x][y].cellType = 1;
                    System.out.println("Hit ship from team 1");
                    if (check_finish(0) == 1) {
                        isFinished = true;
                        winningTeamId = 1;
                    }
                    return true;
                case 5:
                    grid[x][y].cellType = 2;
                    System.out.println("Hit ship from team 2");
                    if (check_finish(1) == 0) {
                        isFinished = true;
                        winningTeamId = 0;
                    }
                    return true;
                case 6:
                    grid[x][y].cellType = 0;
                    System.out.println("Discovered perk 1");
                    return true;
                case 7:
                    grid[x][y].cellType = 0;
                    System.out.println("Discovered perk 2");
                    return true;
                default:
                    return false;
            }
        } else {
            System.out.println("Out of bounds, try again " + x + " " + y);
            return false;
        }
    }


    public void printGrid(int team_id) {
        String output = "";
        if (team_id == 0) {
            output += "Team 1 view\n";
        } else if (team_id == 1) {
            output += "Team 2 view\n";
        } else {
            output = "Unknown team id";
            return;
        }

        output += "----------------------\n";
        for (int i = 0; i < y_size; i++) {
            output += "|";
            for (int j = 0; j < this.x_size; j++) {
                if (grid[i][j].cellType == 0) output += " ";
                else if (grid[i][j].cellType == 1 || grid[i][j].cellType == 2) output += "X";
                else if (grid[i][j].cellType == 4) {
                    output += (team_id == 0) ? "$" : "^";
                } else if (grid[i][j].cellType == 5) {
                    output += (team_id == 1) ? "$" : "^";
                } else output += "^";
                output += " ";
            }
            output += "|\n";
        }
        output += "----------------------";
        System.out.println(output);
    }

    //TODO implement looking in ships array
    private int check_finish(int team_id) {
        boolean allTeam1ShipsHit = true;
        boolean allTeam2ShipsHit = true;

        for (int i = 0; i < x_size; i++) {
            for (int j = 0; j < y_size; j++) {
                // Check for remaining team 1 ships (undiscovered or discovered but not hit)
                if (grid[i][j].cellType == 4) {
                    allTeam1ShipsHit = false;
                }
                // Check for remaining team 2 ships (undiscovered or discovered but not hit)
                if (grid[i][j].cellType == 5) {
                    allTeam2ShipsHit = false;
                }
            }
        }

        // If all team 1 ships are hit, team 2 wins
        if (allTeam1ShipsHit && team_id == 0) {
            return 1;  // Team 2 wins
        }

        // If all team 2 ships are hit, team 1 wins
        if (allTeam2ShipsHit && team_id == 1) {
            return 0;  // Team 1 wins
        }

        // Game is not finished
        return -1;
    }

}
