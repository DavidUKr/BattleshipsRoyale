package org.app.battleshiproyale.model;

import lombok.Getter;

@Getter
public class HitDTO {

    private int x;
    private int y;

    public HitDTO(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
