package org.app.battleshiproyale.game;

import org.app.battleshiproyale.game.game_elements.BattleGrid;
import org.app.battleshiproyale.game.game_elements.ships.*;

public class RunGame {
    public static void main(String[] args) {
        System.out.println("Running Game");

        BattleGrid battleGrid = new BattleGrid();

        BaseShip ship1=new MotherShip();
        BaseShip ship2=new SmallShip();

        System.out.println(ship1.getLength());
        System.out.println(ship2.getLength());
    }
}
