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
    private final int x_size;
    @Getter
    private final int y_size;
    @Getter@Setter
    private int x_divider;

    public final int[][] grid;
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

    public BattleGrid(){//int x_size, int y_size, int x_divider) {
        this.x_size = 10;
        this.y_size = 10;
        grid=new int[][]{
                {3,3,3,3,3,3,3,3,3,3},
                {7,3,4,3,3,3,3,7,3,3},
                {3,3,4,3,3,3,3,6,3,3},
                {3,3,4,3,3,3,3,3,3,3},
                {3,3,3,3,3,3,3,3,3,3},
                {3,3,3,6,3,3,3,3,3,3},
                {3,3,3,3,3,3,5,5,5,3},
                {3,6,3,3,3,3,3,3,3,3},
                {3,3,3,3,3,3,3,3,6,3},
                {3,3,7,3,3,3,7,3,3,3},
        };
    }

    public boolean place_ship(BaseShip ship, int x, int y, int orientation)
    {
        //orientation: 0-horizontal, 1-vertical
        //assigning borders
        int left;
        int right;
        int up=y_size;
        int down=0;
        if (ship.getTeam_id()==0){
            left=0;
            right=x_divider;
        }
        else if (ship.getTeam_id()==1){
            left=x_divider+1;
            right=x_size;
        }
        else {
            System.out.println("Unknown team id");
            return false;
        }
        //TODO finish ship assigning logic
        if(x<right && x>left && y<up && y>down){
            //check fit with orientation
            if(orientation==0 && x+ship.getLength()-1<right){
                ships.add(ship);
                for(int i=0; i<ship.getLength(); i++){
                    grid[x+i][y]=5;
                }
                return true;
            }
            else if(orientation==1 && y-ship.getLength()+1>0){
                ships.add(ship);
                for(int i=0; i<ship.getLength(); i++){
                    grid[x][y-i]=5;
                }
                return true;
            }
            else {
                System.out.println("Ship "+ship+" did not fit because of length and orientation");
                return false;
            }
        }
        return false;
    }

    public boolean hit(int x, int y, int team_id){
        if(team_id==0 || team_id==1) {
            System.out.print("Player " + (team_id + 1) + ":");
        }
        else System.out.println("Player " + team_id + ": unknown team id");
        if(x<x_size && x>0 && y<y_size && y>0) {
            switch (grid[x][y]) {
                case 0:
                    System.out.println("Already empty");
                    return false;
                case 1, 2:
                    System.out.println("Already hit");
                    return false;
                case 3:
                    grid[x][y] = 0;
                    System.out.println("Hit empty");
                    return true;
                case 4:
                    grid[x][y] = 1;
                    System.out.println("Hit ship from team 1");
                    return true;
                case 5:
                    grid[x][y] = 2;
                    System.out.println("Hit ship from team 2");
                    return true;
                case 6:
                    grid[x][y] = 0;
                    System.out.println("Discovered perk 1");
                    return true;
                case 7:
                    grid[x][y] = 0;
                    System.out.println("Discovered perk 2");
                    return true;
                default:
                    return false;
            }
        }
        else {
            System.out.println("Out of bounds, try again");
            return false;
        }
    }

    public void printGrid(int team_id){
        String output="";
        if(team_id==0){
            output+="Team 1 view\n";
        }
        else if(team_id==1){
            output+="Team 2 view\n";
        }
        else {
            output = "Unknown team id";
            return;
        }

        output+="----------------------\n";
        for(int i=0; i<y_size; i++){
            output+="|";
            for(int j=0; j<this.x_size; j++){
                if(grid[i][j]==0) output+=" ";
                else if(grid[i][j]==1 || grid[i][j]==2) output+="X";
                else if(grid[i][j]==4) {
                    if(team_id==0)
                        output += "$";
                    else output+= "^";
                }
                else if(grid[i][j]==5)
                    if(team_id==1)
                        output += "$";
                    else output+= "^";
                else output+="^";
                output+=" ";
            }
            output+="|\n";
        }
        output+="----------------------";
        System.out.println(output);
    }
}
