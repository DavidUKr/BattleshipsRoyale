package org.app.battleshiproyale.model;

import lombok.Data;
import org.app.battleshiproyale.game.game_elements.ships.BaseShip;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerMap {
    private List<BaseShip> ships;

    public PlayerMap(){
        ships = new ArrayList<>();
    }

    public void addShip(BaseShip ship){
        ships.add(ship);
    }
}
