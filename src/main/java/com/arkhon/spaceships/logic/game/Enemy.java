package com.arkhon.spaceships.logic.game;

import com.arkhon.spaceships.logic.machines.Ship;

public class Enemy {
    
    public final Ship ship;
    public final double delay;
    public final Position startPosition;
    
    public Enemy(Ship ship, double delay, Position startPosition){
    
        this.ship = ship;
        this.ship.setIsActive(false);
        this.delay = delay;
        this.startPosition = startPosition;
    }
    
    public void activate() {
    
        ship.setPosition(startPosition);
        ship.setIsActive(true);
    }
    
    public Enemy copyEnemy(){
        return new Enemy(ship,delay,startPosition);
    }
}
