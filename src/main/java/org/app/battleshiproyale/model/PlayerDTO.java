package org.app.battleshiproyale.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.battleshiproyale.game.game_elements.ships.BaseShip;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PlayerDTO {
    String id;
    int stamina;
    List<ShipHiddenDTO> ships=new ArrayList<>();

    public PlayerDTO(String id, int stamina, List<ShipHiddenDTO> ships) {
        this.id = id;
        this.stamina = stamina;
        this.ships = ships;
    }

    public PlayerDTO(String id, int stamina) {
        this.id = id;
        this.stamina = stamina;
    }

    public void addShip(ShipHiddenDTO ship) {
        ships.add(ship);
    }
}
