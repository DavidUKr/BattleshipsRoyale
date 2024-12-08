package org.app.battleshiproyale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipHiddenDTO {
    ShipType shipType;
    int damage;
    boolean isAlive;
}
