package com.arkhon.spaceships.gui.tests;

import com.arkhon.spaceships.logic.game.Game;
import com.arkhon.spaceships.logic.machines.exceptions.OverTheMaxCargoSpaceException;
import java.io.IOException;

public class TestMain {
    
    public static void main(String[] args) throws OverTheMaxCargoSpaceException, IOException{
    
        Game game = Game.GameFactory();
        game.run();
    }
    
}
