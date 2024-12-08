package org.app.battleshiproyale.utils;

import org.app.battleshiproyale.game.game_elements.ships.BaseShip;
import org.app.battleshiproyale.game.game_elements.ships.MediumShip;
import org.app.battleshiproyale.game.game_elements.ships.MotherShip;
import org.app.battleshiproyale.game.game_elements.ships.SmallShip;
import org.app.battleshiproyale.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShipUtils {

    public BaseShip getShipFromDTO(ShipDTO shipDTO, String player_id) {

        BaseShip ship = null;
        switch (shipDTO.getShipType()) {
            case MOTHER_SHIP -> ship = new MotherShip(player_id);
            case MEDIUM_SHIP -> ship = new MediumShip(player_id);
            case SMALL_SHIP -> ship = new SmallShip(player_id);
        }
        List<Point> coordinates = new ArrayList<>();
        for(int i=0; i<ship.getLength();i++){
            if (shipDTO.getOrientation() == ShipOrientation.HORIZONTAL) {
                coordinates.add(new Point(shipDTO.getFirstCell_coordinates().getX()+ i, shipDTO.getFirstCell_coordinates().getY()));
            }
            else if (shipDTO.getOrientation() == ShipOrientation.VERTICAL) {
                coordinates.add(new Point(shipDTO.getFirstCell_coordinates().getX(), shipDTO.getFirstCell_coordinates().getY()+1));
            }
        }
        ship.setCoordinates(coordinates);

        return ship;
    }

    public ShipHiddenDTO getShipHiddenDTO(BaseShip ship) {
        ShipHiddenDTO shipHiddenDTO = new ShipHiddenDTO();
        switch(ship.getLength()){
            case 5: shipHiddenDTO.setShipType(ShipType.MOTHER_SHIP); break;
            case 3: shipHiddenDTO.setShipType(ShipType.MEDIUM_SHIP); break;
            case 2: shipHiddenDTO.setShipType(ShipType.SMALL_SHIP); break;
        }
        shipHiddenDTO.setAlive(ship.isAlive());
        return shipHiddenDTO;
    }
}
