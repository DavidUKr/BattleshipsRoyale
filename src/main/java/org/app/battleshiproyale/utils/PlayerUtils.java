package org.app.battleshiproyale.utils;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.model.Player;
import org.app.battleshiproyale.model.PlayerDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerUtils {
    private final ShipUtils shipUtils;

    public PlayerDTO getDTOfromPlayerNoShips(Player player) {
        return new PlayerDTO(player.getId(), player.getStamina());
    }

    public PlayerDTO getDTOfromPlayerWShips(Player player) {
        return new PlayerDTO(player.getId(), player.getStamina(), player.getShips().stream().map(shipUtils::getShipHiddenDTO).toList());
    }
}
