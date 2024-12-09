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
        this.stamina = 0;
        this.ships = new ArrayList<>();
    }

    public void increaseStamina(int amount) {
        this.stamina+=amount;
    }
}



