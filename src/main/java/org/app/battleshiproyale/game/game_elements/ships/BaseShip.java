package org.app.battleshiproyale.game.game_elements.ships;

import lombok.Getter;
import lombok.Setter;

abstract public class BaseShip {
    @Getter
    protected int length;
    @Getter
    protected int damage=0;
    @Getter
    protected boolean isAlive=true;
    @Getter @Setter
    protected int team_id;

    public BaseShip(int team_id) {
        this.team_id = team_id;
    }

    public boolean apply_damage() {
        if(damage==length-1) isAlive=false;
        else damage++;

        return isAlive;
    }
}
