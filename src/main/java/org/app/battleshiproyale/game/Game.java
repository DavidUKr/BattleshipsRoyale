package org.app.battleshiproyale.game;

import org.app.battleshiproyale.model.*;

import java.util.ArrayList;
import java.util.List;

public interface Game {
    boolean joinPlayer(String playerId);

    List<String> getJoinedPlayersIds();

    boolean placePlayerShips(String playerId, PlayerMap playerMap);

    void setPlayerReady(String playerId);

    ArrayList<Player> getReadyPlayersIds();

    void resetSession();

    HitResult hit(String playerId, HitDTO hitDTO);//look into HitResult //

    boolean usePerk(String playerId, PerkDTO perkDTO);

    int getPlayerStamina (String player_id);

    GameState getGameState();

    GameState resetBoard();//look into model.GameState

    void startGame();
}
