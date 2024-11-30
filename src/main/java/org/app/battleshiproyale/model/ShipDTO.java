package org.app.battleshiproyale.model;

import lombok.Data;

@Data
public class ShipDTO {

    String name;
    ShipType shipType;
    int first_cell_x;
    int first_cell_y;
    ShipOrientation orientation;
}