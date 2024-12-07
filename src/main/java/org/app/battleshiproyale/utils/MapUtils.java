package org.app.battleshiproyale.utils;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.model.PlayerMap;
import org.app.battleshiproyale.model.PlayerMapDTO;
import org.app.battleshiproyale.model.ShipDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapUtils {

    private final ShipUtils shipUtils;

    public PlayerMap getPlayerMapFromDTO(PlayerMapDTO playerMapDTO, String player_id) {
        PlayerMap playerMap = new PlayerMap();
        for (ShipDTO shipDTO : playerMapDTO.getShips()) {
            playerMap.addShip(shipUtils.getShipFromDTO(shipDTO, player_id));
        }

        return playerMap;
    }
}
