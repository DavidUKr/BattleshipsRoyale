package org.app.battleshiproyale.model;

import lombok.Data;
import org.app.battleshiproyale.game.game_elements.ships.BaseShip;

import java.util.ArrayList;
import java.util.List;

@Data
public class Player {
    private final String id; // Track visited cells
    private int stamina;
    private List<BaseShip> ships;
    private boolean isReadyForBattle=false;

    public Player(String id) {
        this.id = id;
        this.stamina = 100;
        this.ships = new ArrayList<>();
    }

    public void increaseStamina(int amount) {
        // Increase stamina and cap it at 100
        this.stamina = Math.min(100, this.stamina + amount);
    }

    public void decreaseStamina(int amount) {
        // Decrease stamina but ensure it doesn't go below 0
        this.stamina = Math.max(0, this.stamina - amount);
    }

    public int getStamina() {
        return this.stamina;
    }
}



