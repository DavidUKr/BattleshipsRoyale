package org.app.battleshiproyale.game.game_elements;

public class BattleGrid {

    char[][] grid;
    int gridSize;

    public BattleGrid(int size) {
        this.gridSize = size;
        this.grid = new char[gridSize][gridSize];

        // Initialize the grid with '-'
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = '-';
            }
        }
    }

    // Print grids for both players with a separator
    void printGrids() {
        System.out.println("Player 1's View:");
        printGrid();  // Print Player 1's grid

        // Print separator
        System.out.println("----------");

        System.out.println("Player 2's View:");
        printGrid();  // Print Player 2's grid
    }

    // Print a single grid (used by both players)
    void printGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();  // New line after each row
        }
    }

    // Mark a square with a player's move
    void markSquare(int player, int x, int y) {
        if (x < 0 || x >= gridSize || y < 0 || y >= gridSize) {
            System.out.println("Invalid move: out of bounds!");
            return;
        }

        if (grid[x][y] == '-') {  // Only mark if the square is empty
            if (player == 1) {
                grid[x][y] = 'X';  // Player 1 marks 'X'
                System.out.println("Player 1 marked X at (" + x + ", " + y + ")");
            } else if (player == 2) {
                grid[x][y] = 'O';  // Player 2 marks 'O'
                System.out.println("Player 2 marked O at (" + x + ", " + y + ")");
            }
        } else {
            System.out.println("Square already marked!");
        }
    }

    // Simulate clearing the terminal by printing new lines
    void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();  // Print empty lines to clear the screen
        }
    }
}

