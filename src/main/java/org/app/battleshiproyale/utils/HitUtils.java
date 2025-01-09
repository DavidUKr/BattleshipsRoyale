package org.app.battleshiproyale.utils;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.game.game_elements.GridCell;
import org.app.battleshiproyale.model.HitResult;
import org.app.battleshiproyale.model.HitResultDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HitUtils {

    public HitResultDTO getDTOfromHitResult(HitResult hitResult) {

        HitResultDTO hitResultDTO = new HitResultDTO();

        if (hitResult.getCellType()== GridCell.CellType.UNDISCOVERED_SHIP_TEAM_1 || hitResult.getCellType()==GridCell.CellType.UNDISCOVERED_SHIP_TEAM_2)
            hitResultDTO.setHitResult(4);
        else hitResultDTO.setHitResult(0);

        hitResultDTO.setMessage(hitResult.getMessage());
        return hitResultDTO;
    }
}
