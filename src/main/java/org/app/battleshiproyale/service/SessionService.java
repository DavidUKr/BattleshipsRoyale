package org.app.battleshiproyale.service;

import org.app.battleshiproyale.model.PlayerMapDTO;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public boolean joinPlayerToBattle(String playerId) {
        return false;
    }

    public void placePlayerShips(String playerId, PlayerMapDTO playerMapDTO) {

    }

    public Integer getPlayersJoinedStatus() {
        return 0;
    }

    public boolean getPlayersReadyStatus() {
        return false;
    }
}
