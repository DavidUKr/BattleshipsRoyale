package org.app.battleshiproyale.model;

import lombok.Data;

@Data
public class ShipDTO {

    ShipType shipType;
    Point firstCell_coordinates;
    ShipOrientation orientation;
}