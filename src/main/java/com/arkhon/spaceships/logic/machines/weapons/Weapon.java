package com.arkhon.spaceships.logic.machines.weapons;

import com.arkhon.spaceships.gui.GameImage;
import com.arkhon.spaceships.logic.game.Direction;
import com.arkhon.spaceships.logic.game.Position;
import com.arkhon.spaceships.logic.machines.Projectile;
import com.arkhon.spaceships.logic.machines.Ship;

public abstract class Weapon {
    
    public enum WeaponTypes{ KINETIC,LASER,PLASMA,MISSILE }
    
    public final String name;
    public final WeaponTypes type;
    public final int damage;
    public final int size;
    public final double rateOfFire; // second/shot
    public final int projectileSpeed;
    public final GameImage projectileImage;
    public final GameImage[] eImage;
    
    private long firedLastTime;

    public Weapon(String name, WeaponTypes type, int damage, int size, double rateOfFire ,int projectileSpeed,GameImage projectileImage, GameImage[] eImage) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.size = size;
        this.rateOfFire = rateOfFire;
        this.projectileSpeed = projectileSpeed;
        this.projectileImage = projectileImage;
        this.eImage = eImage;
        
        this.firedLastTime = 0;
    }
    
    public Projectile fire(Ship ship, long time){
        
        if(!canFire(time)) { return null; }
        
        firedLastTime = time;
        Position pos;
        int deltaX = ship.width/2-projectileImage.getWidth()/2;
        Direction dir = ship.getDirection();
        switch(dir){
        
            case NORTH: 
                pos = new Position(ship.getPosition().x+deltaX, ship.getPosition().y-projectileImage.getHeight()-10);
                break;
            case SOUTH:
                pos = new Position(ship.getPosition().x+deltaX, ship.getPosition().y+ship.height+10);
                break;
            default:
                return null;
        }
        
        Projectile p = new Projectile(pos, dir, projectileSpeed, damage, projectileImage,eImage);
        return p;
    }
    
    public boolean canFire(long time){  
        return (time-firedLastTime) > (1000/rateOfFire);
    }
}
