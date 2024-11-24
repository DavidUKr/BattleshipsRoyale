package org.app.battleshiproyale.game;

import org.app.battleshiproyale.model.*;

public interface Game {
    boolean joinPlayer(String playerId);

    void placePlayerShips(String playerId, PlayerMapDTO playerMapDTO);

    void setPlayerReady(String playerId);

    boolean getAllPlayersJoinedCount(); //0,1,2

    boolean getAllPlayersReadyCount(); //0,1,2

    GameState resetBoard(); //look into model.GameState

    HitResult hit(String playerId, HitDTO hitDTO);//look into HitResult //

    boolean usePerk(String playerId, PerkDTO perkDTO);

    int getPlayerStamina (String player_id);
}
