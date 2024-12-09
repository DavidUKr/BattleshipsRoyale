package org.app.battleshiproyale.utils;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.game.game_elements.GridCell;
import org.app.battleshiproyale.model.GridDTO;
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
        int ship_index=0;
        for (ShipDTO shipDTO : playerMapDTO.getShips()) {
            playerMap.addShip(shipUtils.getShipFromDTO(shipDTO, player_id, ship_index++));
        }

        return playerMap;
    }

    public GridDTO getDTOfromGridMatrix(GridCell[][] grid, int player_index , int max_x, int max_y) {
        Integer[][] dtoGrid=new Integer[max_x][max_y];
        for (int i = 0; i < max_x; i++) {
            for (int j = 0; j < max_y; j++) {
                // output Integer[][]
                // 0 - discovered empty
                // 1 - undiscovered
                // 2 - my ship hit
                // 3 - my ship not hit
                // 4 - enemy ship hit
                switch (grid[i][j].cellType) {
                    case 0: //discovered empty
                        dtoGrid[i][j] = 0;
                        break;
                    case 1: //discovered player0
                        if (player_index==0)
                            dtoGrid[i][j] = 2;
                        else
                            dtoGrid[i][j] = 4;
                        break;
                    case 2: //discovered player1
                        if (player_index==1)
                            dtoGrid[i][j] = 2;
                        else
                            dtoGrid[i][j] = 4;
                        break;
                    case 3, 6, 7: //undiscovered empty, perk type1, perk type2
                        dtoGrid[i][j] = 1;
                        break;
                    case 4: //undiscovered player0
                        if (player_index==0)
                            dtoGrid[i][j] = 3;
                        else
                            dtoGrid[i][j] = 1;
                        break;
                    case 5: //undiscovered player1
                        if (player_index==1)
                            dtoGrid[i][j] = 3;
                        else
                            dtoGrid[i][j] = 1;
                        break;
                    default: //unknown
                        dtoGrid[i][j] = 9;
                }
            }
        }
        return new GridDTO(dtoGrid);
    }
}
