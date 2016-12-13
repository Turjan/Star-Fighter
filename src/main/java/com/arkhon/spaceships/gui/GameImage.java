package com.arkhon.spaceships.gui;

import com.arkhon.spaceships.logic.game.Direction;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum GameImage {
    
   BASIC_SHIP_EXPLOSION0("src/main/resources/images/basic_ship_explosion0.png"),
   BASIC_SHIP_EXPLOSION1("src/main/resources/images/basic_ship_explosion1.png"),
   BASIC_SHIP_EXPLOSION2("src/main/resources/images/basic_ship_explosion2.png"),
   BASIC_SHIP_EXPLOSION3("src/main/resources/images/basic_ship_explosion3.png"),
   BASIC_SHIP_EXPLOSION4("src/main/resources/images/basic_ship_explosion4.png"),
   BASIC_SHIP_EXPLOSION5("src/main/resources/images/basic_ship_explosion5.png"),
   BASIC_SHIP_EXPLOSION6("src/main/resources/images/basic_ship_explosion6.png"),
   BASIC_SHIP_EXPLOSION7("src/main/resources/images/basic_ship_explosion7.png"),
   BASIC_SHIP_EXPLOSION8("src/main/resources/images/basic_ship_explosion8.png"),
   BASIC_SHIP_EXPLOSION9("src/main/resources/images/basic_ship_explosion9.png"),
   SMALLEXPLOSION("src/main/resources/images/smallexplosion.png"),
   
   CONTROLPANEL("src/main/resources/images/controlPanel.png"),
   BACKGROUNDLVL1("src/main/resources/images/stars.jpg"),
   
   CONDORSHIP("src/main/resources/images/condorship.png"),
   HUNTERSHIP("src/main/resources/images/huntership.png"),
   SCARABSHIP("src/main/resources/images/scarabship.png"),
   
   CONDORGUN("src/main/resources/images/condorshot.png"),
   RAILGUN("src/main/resources/images/railgunshot.png"),
   SLASERGUN("src/main/resources/images/slasershot.png"),
   PLASMAGUN("src/main/resources/images/plasmashot.png");
   

   private BufferedImage image;

   private GameImage(String filename) {
        try                     { image = ImageIO.read(new File(filename)); }
        catch (IOException e)   { System.out.println("Can't load image");image = null; }
   }
   
   public BufferedImage getImage(){ return image; }
   public BufferedImage getTransformedImage(Direction direction){
       
            BufferedImage result = image;
            switch(direction){
                case NORTH:
                    break;
                case SOUTH:
                    AffineTransform tx = new AffineTransform();
                    AffineTransformOp op;
                    tx.rotate(Math.toRadians(180), result.getWidth()/2 , result.getHeight()/2);
                    op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
                    result = op.filter(result, null);
                    break;
            }
            return result;
   }
   
   public int getHeight(){ return image.getHeight(); }
   public int getWidth() { return image.getWidth(); }
}

// If the image is not showing then probably the path is wrong. In that case the game will not crash, just simply not display the image.
