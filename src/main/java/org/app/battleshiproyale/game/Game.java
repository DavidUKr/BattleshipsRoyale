package org.app.battleshiproyale.game;

import org.app.battleshiproyale.model.*;

import java.util.List;

public interface Game {
    boolean joinPlayer(String playerId);

    List<String> getJoinedPlayersIds();

    void placePlayerShips(String playerId, PlayerMapDTO playerMapDTO);

    void setPlayerReady(String playerId);

    List<String> getReadyPlayersIds();

    void resetSession();

    HitResult hit(String playerId, HitDTO hitDTO);//look into HitResult //

    boolean usePerk(String playerId, PerkDTO perkDTO);

    int getPlayerStamina (String player_id);

    GameState resetBoard();//look into model.GameState

    void startGame();
}
