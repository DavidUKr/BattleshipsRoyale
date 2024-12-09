package org.app.battleshiproyale.game.game_elements;

import lombok.Getter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
    @Getter
    private final int playerId;
    private final Random random = new Random(); // For generating random moves
    private final BattleGrid battleGrid;
    private final Set<String> visitedCells = new HashSet<>();  // Track visited cells

    public Player(int playerId, BattleGrid battleGrid) {
        this.playerId = playerId;
        this.battleGrid = battleGrid;
    }
}



