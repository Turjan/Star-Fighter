package com.arkhon.spaceships.logic.machines;

import com.arkhon.spaceships.gui.GameImage;
import com.arkhon.spaceships.logic.game.Position;
import java.awt.image.BufferedImage;

public class Explosion {
    
    private Position position;
    private int lifeTime;
    private boolean isActive;
    private GameImage[] images;
    private BufferedImage currentImage;

    public Explosion(SpaceObject o) {
        position = o.position;
        isActive = true;
        images = o.explosionImages;
        currentImage = images[0].getImage();
        lifeTime = images.length;
        //System.out.println("Explosion from " + o);
    }

    public Position getPosition()               { return position; }
    public void setPosition(Position position)  { this.position = position;}

    public boolean isActive()                   { return isActive; }
    public void setIsActive(boolean isActive)   { this.isActive = isActive; }

    public BufferedImage getCurrentImage()                  { return currentImage; }
    public void setCurrentImage(BufferedImage currentImage) { this.currentImage = currentImage; }
    
    public void decreaseLifeTime(){ 
        
        currentImage = images[images.length-lifeTime].getImage();
        lifeTime--;
        isActive = lifeTime > 0;
    }
    
    
}
