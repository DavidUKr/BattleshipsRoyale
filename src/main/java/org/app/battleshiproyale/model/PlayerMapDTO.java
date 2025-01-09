package org.app.battleshiproyale.model;

import lombok.Data;

import java.util.List;

@Data
public class PlayerMapDTO {
    List<ShipDTO> ships;
}
