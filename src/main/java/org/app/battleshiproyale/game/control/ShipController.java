package org.app.battleshiproyale.game.control;

import lombok.RequiredArgsConstructor;
import org.app.battleshiproyale.game.model.ShipDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping(value="BattleshipRoyale/api/v1/ships")
@RequiredArgsConstructor
public class ShipController {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void placeShip(@RequestBody ShipDTO ship){

    }
}
