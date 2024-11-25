package org.app.battleshiproyale.model;

import lombok.Data;
import org.app.battleshiproyale.game.game_elements.ships.BaseShip;

import java.util.List;

@Data
public class Player {
    private final String id; // Track visited cells
    List<BaseShip> ships;

    public Player(String id) {
        this.id = id;
    }
}



