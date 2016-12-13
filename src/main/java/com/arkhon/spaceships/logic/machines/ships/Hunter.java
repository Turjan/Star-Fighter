package com.arkhon.spaceships.logic.machines.ships;

import com.arkhon.spaceships.gui.GameImage;
import static com.arkhon.spaceships.gui.GameImage.HUNTERSHIP;
import com.arkhon.spaceships.logic.game.Direction;
import com.arkhon.spaceships.logic.game.Position;
import com.arkhon.spaceships.logic.machines.Ship;
import com.arkhon.spaceships.logic.machines.exceptions.OverTheMaxCargoSpaceException;
import com.arkhon.spaceships.logic.machines.weapons.SlaserGun;

public class Hunter extends Ship{

    public static final GameImage shipImage = HUNTERSHIP;
    public static final GameImage[] eImage = basicShipExplosion;
    public static final int startSpeed = 1;
    
    public Hunter(Position position, Direction direction){
        super("Hunter", position, direction, startSpeed, shipImage, eImage);
        this.setHealth(5);
        this.setHull(1);
        this.setCargoSpace(3);
        this.weapons.add(new SlaserGun());
        this.setActiveWeapon(0);
    }
}
