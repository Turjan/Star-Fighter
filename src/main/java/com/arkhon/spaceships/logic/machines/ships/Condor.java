package com.arkhon.spaceships.logic.machines.ships;

import com.arkhon.spaceships.logic.machines.weapons.CondorGun;
import com.arkhon.spaceships.gui.GameImage;
import static com.arkhon.spaceships.gui.GameImage.CONDORSHIP;
import com.arkhon.spaceships.logic.game.Direction;
import com.arkhon.spaceships.logic.game.Position;
import com.arkhon.spaceships.logic.machines.Ship;
import com.arkhon.spaceships.logic.machines.exceptions.OverTheMaxCargoSpaceException;
import com.arkhon.spaceships.logic.machines.weapons.RailGun;

public class Condor extends Ship{
    
    public static final GameImage shipImage = CONDORSHIP;
    public static final GameImage[] eImage = basicShipExplosion;
    public static final int startSpeed = 1;
    
    public Condor(Position position, Direction direction) {
        super("Condor", position, direction, startSpeed, shipImage, eImage);
        this.setHealth(10);
        this.setHull(4);
        this.setCargoSpace(10);
        this.weapons.add(new CondorGun());
        this.weapons.add(new RailGun());
        this.setActiveWeapon(0);
    }
}
