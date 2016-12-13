package com.arkhon.spaceships.logic.machines;

import com.arkhon.spaceships.gui.GameImage;
import com.arkhon.spaceships.logic.game.Direction;
import com.arkhon.spaceships.logic.game.Position;

public class Projectile extends SpaceObject{

    private final int damage;
    
    public Projectile(Position position, Direction direction, int speed, int damage, GameImage image, GameImage[] explosion) {
        super(  position, 
                direction, 
                speed, 
                image, 
                explosion);
        
        this.damage = damage;
    }
    
    @Override
    public boolean damageReceive(int i){
    
        isActive = false;
        return false;
    }
    
    @Override
    public int damageDealt() { return damage; }
}
