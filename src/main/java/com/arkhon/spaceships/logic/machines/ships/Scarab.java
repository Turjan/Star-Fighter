package com.arkhon.spaceships.logic.machines.ships;

import com.arkhon.spaceships.gui.GameImage;
import static com.arkhon.spaceships.gui.GameImage.SCARABSHIP;
import com.arkhon.spaceships.logic.game.Direction;
import com.arkhon.spaceships.logic.game.Position;
import static com.arkhon.spaceships.logic.machines.Explosive.basicShipExplosion;
import com.arkhon.spaceships.logic.machines.Ship;
import com.arkhon.spaceships.logic.machines.exceptions.OverTheMaxCargoSpaceException;
import com.arkhon.spaceships.logic.machines.weapons.PlasmaGun;

public class Scarab extends Ship{

    public static final GameImage shipImage = SCARABSHIP;
    public static final GameImage[] eImage = basicShipExplosion;
    public static final int startSpeed = 1;
    
    public Scarab(Position position, Direction direction) {
        super("Scarab", position, direction, startSpeed, shipImage,eImage);
        this.setHealth(10);
        this.setHull(2);
        this.setCargoSpace(6);
        this.weapons.add(new PlasmaGun());
        this.setActiveWeapon(0);
    }
}
