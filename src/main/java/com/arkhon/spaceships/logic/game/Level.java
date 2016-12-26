package com.arkhon.spaceships.logic.game;

import com.arkhon.spaceships.gui.GameImage;
import com.arkhon.spaceships.logic.game.exceptions.WrongDataException;
import com.arkhon.spaceships.logic.machines.ships.*;
import com.arkhon.spaceships.logic.machines.Ship;
import com.arkhon.spaceships.logic.machines.exceptions.OverTheMaxCargoSpaceException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Level implements Comparable<Level>{
    
    public final int levelNumber;
    public final String name;
    public final GameImage backgroundImage;
    public final int levelWidth;
    public final int levelHeight;
    private final ArrayList<Enemy> enemies;

    public static Level levelFactory(File file) throws FileNotFoundException, OverTheMaxCargoSpaceException, WrongDataException{
    
        try(Scanner sc = new Scanner(file)){
        
            int levelWidth = 800;
            int levelHeight = 600;
            int levelNumber = Integer.valueOf(sc.nextLine());
            String name = sc.nextLine();
            GameImage backgroundImage = GameImage.valueOf(sc.nextLine());
            
            ArrayList<Enemy> enemies = new ArrayList<>();
            while(sc.hasNext()){
                String[] data = sc.nextLine().split(" ");
                Ship ship = null;
                switch(data[0]){
                
                    case "Condor": ship = new Condor(null, Direction.SOUTH);break;
                    case "Hunter": ship = new Hunter(null, Direction.SOUTH);break;
                    case "Scarab": ship = new Scarab(null, Direction.SOUTH);break; 
                    default: throw new WrongDataException("Not existing ship class found in " + file.getName());    
                }
                enemies.add(new Enemy(ship, Integer.valueOf(data[3]), new Position(Integer.valueOf(data[1]), Integer.valueOf(data[2]))));
            }
            return new Level(levelNumber, name, backgroundImage, levelWidth, levelHeight, enemies);
        }
    }
    
    private Level(int levelNumber, String name, GameImage backgroundImage, int levelWidth, int levelHeight, ArrayList<Enemy> enemies) {
        this.levelNumber = levelNumber;
        this.name = name;
        this.backgroundImage = backgroundImage;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.enemies = enemies;
    }

    public ArrayList<Enemy> getEnemys()  { 

        ArrayList<Enemy> copy = new ArrayList<>();
        for(Enemy e:enemies){ copy.add(e.copyEnemy()); }
        return copy; 
    }

    @Override
    public int compareTo(Level o) {
        return this.levelNumber-o.levelNumber;
    }
    
    
}
