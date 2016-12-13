package com.arkhon.spaceships.logic.machines;

import com.arkhon.spaceships.logic.machines.weapons.Weapon;
import com.arkhon.spaceships.gui.GameImage;
import com.arkhon.spaceships.logic.game.Direction;
import com.arkhon.spaceships.logic.game.Position;
import com.arkhon.spaceships.logic.machines.exceptions.OverTheMaxCargoSpaceException;
import java.util.ArrayList;

public abstract class Ship extends SpaceObject {
    
    public final String name;
    protected int health;
    protected int cargoSpace;
    protected int hull;
    protected Weapon activeWeapon;
    protected final ArrayList<Weapon> weapons;
    protected final ArrayList<Shield> shields;
    
    public Ship(String name, Position position, Direction direction, int speed, GameImage image, GameImage[] explosion){ 
        super(position, direction, speed, image, explosion);
        this.name = name;
        weapons = new ArrayList<>();
        shields = new ArrayList<>();
    }

    public int getHealth()              { return health;}
    public void setHealth(int health)   { this.health = health;}

    public int getCargoSpace()                  { return cargoSpace;}
    public void setCargoSpace(int cargoSpace)   { this.cargoSpace = cargoSpace;}

    public int getHull()            { return hull;}
    public void setHull(int hull)   { this.hull = hull;}
    
    public ArrayList<Weapon> getWeapons() { return weapons; }
    public ArrayList<Shield> getShields() { return shields; }
    
    public Weapon getActiveWeapon(){ return activeWeapon;}
    public void setActiveWeapon(int i){
    
        if(i<0 || i>=weapons.size()) { return; }
        else                         { activeWeapon = weapons.get(i);}
    }
    
    public void addWeapon(Weapon weapon) throws OverTheMaxCargoSpaceException { 
        if(getFreeCargoSpace() < weapon.size) { 
            throw new OverTheMaxCargoSpaceException("No space left in ship "+name); 
        }
        else { weapons.add(weapon); }
    }
    
    public void addShield(Shield shield) throws OverTheMaxCargoSpaceException { 
        if(getFreeCargoSpace() < shield.size) { 
            throw new OverTheMaxCargoSpaceException("No space left in ship "+name); 
        }
        else { shields.add(shield); }
    }
    
    public int getFreeCargoSpace() throws OverTheMaxCargoSpaceException{
    
        int occupied = 0;
        for(Weapon weapon:weapons){ occupied += weapon.size; }
        for(Shield shield:shields ){ occupied += shield.size; }
        if(occupied>cargoSpace) {
            String message = name +" ship has too much equipmenet "+ "max:"+cargoSpace +" current:"+occupied;
            throw new OverTheMaxCargoSpaceException(message); 
        }
        return cargoSpace - occupied;
    }
    
    @Override
    public boolean damageReceive(int damage){  //return with true if the ship survived the damageReceive, false if not
    
        if(damage >= hull)  { health -= damage - hull; }
        if(health < 0)      { isActive = false; }
        return (isActive);
    }
    
    @Override
    public int damageDealt(){ return health+hull; }  //damage dealt when the ship collides with its enemy (it's projectile a separeta object)
}
