package org.app.battleshiproyale.game.game_elements.ships;

import lombok.Getter;
import lombok.Setter;
import org.app.battleshiproyale.model.Point;

import java.util.List;

abstract public class BaseShip {
    @Getter
    protected int length;
    @Getter
    protected int damage=0;
    @Getter
    protected boolean isAlive=true;
    @Getter @Setter
    protected String player_id;
    @Getter @Setter
    private List<Point> coordinates;

    public BaseShip(String player_id) {
        this.player_id = player_id;
    }

    public boolean apply_damage() {
        if(damage==length-1) isAlive=false;
        else damage++;

        return isAlive;
    }

    public boolean isDestroyed() {
        return !isAlive;
    }
}
