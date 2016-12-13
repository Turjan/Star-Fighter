package com.arkhon.spaceships.logic.machines;

import com.arkhon.spaceships.gui.GameImage;
import com.arkhon.spaceships.logic.game.Direction;
import com.arkhon.spaceships.logic.game.Position;
import java.awt.image.BufferedImage;

public abstract class SpaceObject implements Explosive{
    
    protected Position position;
    protected Direction direction;
    protected int speed;
    public final int width;
    public final int height;
    public final BufferedImage image;
    public final GameImage[] explosionImages;
    protected boolean isActive;
    
    public SpaceObject(Position position, Direction direction, int speed, GameImage image, GameImage[] explosionImages) {
        this.position = position;
        this.direction = direction;
        this.speed = speed;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.image = image.getTransformedImage(direction);
        this.explosionImages = explosionImages;
        isActive = true;
    }

    public Position getPosition()               { return position; }
    public void setPosition(Position position)  { this.position = position; }

    public Direction getDirection()                 { return direction; }
    public void setDirection(Direction direction)   { this.direction = direction;}
    
    public int getSpeed()               { return speed; }
    public void setSpeed(int speed)     { this.speed = speed; }

    public boolean isActive()                   { return isActive; }
    public void setIsActive(boolean isActive)   { this.isActive = isActive; }
    
    public boolean inArea(int width, int height){  //in the area fully or not
        
        if(position.x < 0 || position.x > width) { return false; }
        if(position.y < 0 || position.y > height){ return false; } 
        return true;
    }
    
    public boolean isCollide(SpaceObject other){  
        
        if(this.equals(other) || !other.isActive) { return false; }  //to not collide with itself
        
        if(position.x > other.position.x &&
           other.width < (position.x - other.position.x))   { return false; }
        
        else if(width < (other.position.x - position.x))    { return false; }
        
        if(position.y > other.position.y &&
           other.height < (position.y - other.position.y))  { return false; }
        
        else if(height < (other.position.y - position.y))   { return false; }
        
        return true;
    }
    
    public abstract boolean damageReceive(int damage);  //deal with the damage received by the object
    public abstract int damageDealt();                  //returns the damage the object causes if it is collides with another object
}
