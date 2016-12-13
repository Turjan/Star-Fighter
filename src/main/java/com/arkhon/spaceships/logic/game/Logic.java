package com.arkhon.spaceships.logic.game;

import com.arkhon.spaceships.logic.machines.Explosion;
import com.arkhon.spaceships.logic.machines.Projectile;
import com.arkhon.spaceships.logic.machines.Ship;
import com.arkhon.spaceships.logic.machines.SpaceObject;
import java.util.ArrayList;

public class Logic {
    
    private final Level currentLevel;
    private final Ship player;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Projectile> projectiles;
    private final ArrayList<Explosion> explosions;
    private boolean lost;
    private boolean victory;
    
    private final long startTime;
    private long currentTime;

    public Logic(Level currentLevel, Ship player) {
        
        this.startTime = System.currentTimeMillis(); // setting up the time
        this.currentTime = startTime;
        
        this.currentLevel = currentLevel;
        this.player = player;
        this.enemies = currentLevel.getEnemys();
        this.projectiles = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.lost = false;
        this.victory = false;
    }
    
    public Level getLevel()                         { return currentLevel; }
    public Ship getPlayer()                         { return player; }
    public ArrayList<Projectile> getProjectiles()   { return projectiles; }
    public ArrayList<Explosion> getExplosions(){ return explosions; }
    public ArrayList<Ship> getActiveShips()         { 
        
        ArrayList<Ship> ships = new ArrayList<>();
        for(Enemy enemy: enemies){
            if(enemy.ship.isActive()){
                ships.add(enemy.ship);
            }
        }
        return ships;
    }
    
    public void update(){
        currentTime = System.currentTimeMillis();
        removeDead();
        deployEnemies();
        enemyMove();
        enemyFire();
        projectileMove();
        checkOutcome();
    }
    
    public void fireByPlayer()              { fire(player); }
    public void changePlayerWeapon(int i)   { player.setActiveWeapon(i); }
    
    public void movePlayer(Position pos){
        int x = pos.x;
        int y = pos.y;
        if(x<0) { x = 0; }
        else if((x+player.width)>currentLevel.levelWidth) { x = currentLevel.levelWidth-player.width; } //only for the player
        if(y<0) { y = 0; }
        else if((y+player.height)>currentLevel.levelHeight) { y = currentLevel.levelHeight-player.height; }
        player.setPosition(new Position(x, y));
        collision(player);
    }
    
    private void move(SpaceObject o){
        
        int x = o.getPosition().x;
        int y = o.getPosition().y;
        switch(o.getDirection()){
            case NORTH: y=y-o.getSpeed(); break;
            case SOUTH: y=y+o.getSpeed(); break;
            case EAST:  x=x+o.getSpeed(); break;
            case WEST:  x=x-o.getSpeed(); break;
        }
        Position newPos = new Position(x, y);
        o.setPosition(newPos);
        if(!o.inArea(currentLevel.levelWidth, currentLevel.levelHeight)){
            o.setIsActive(false);
        }
        
        collision(o);
    }
    
    private void collision(SpaceObject o){ //collision with player TODO
        for(Projectile p:projectiles){
            if(o.isCollide(p)){
                int dam = o.damageDealt();
                if(!o.damageReceive(p.damageDealt()))   { explosions.add(new Explosion(o)); }
                if(!p.damageReceive(dam))               { explosions.add(new Explosion(p)); }
            }
        }
        for(Enemy enemy:enemies){
            if(o.isCollide(enemy.ship)){
                int dam = o.damageDealt();
                if(!o.damageReceive(enemy.ship.damageDealt()))   { explosions.add(new Explosion(o));}
                if(!enemy.ship.damageReceive(dam))               { explosions.add(new Explosion(enemy.ship));}
            }
        }
        if(o.isCollide(player)){
            int dam = o.damageDealt();
            if(!o.damageReceive(player.damageDealt()))      { explosions.add(new Explosion(o));}
            if(!player.damageReceive(dam))                  { explosions.add(new Explosion(player));}
        }
    }
    
    private void fire(Ship ship){
    
        Projectile p = ship.getActiveWeapon().fire(ship, currentTime);
        if(p != null) { projectiles.add(p); }
    }
   
    private void removeDead() {
        int deltaStartTime = (int)(currentTime-startTime)/1000;
        for(int i=0;i<projectiles.size();i++){
            if(!projectiles.get(i).isActive()){ projectiles.remove(i); }
        }
        for(int i=0;i<enemies.size();i++){
            if(!enemies.get(i).ship.isActive() && deltaStartTime > enemies.get(i).delay){ enemies.remove(i); }
        }
        for(int i=0;i<explosions.size();i++){
            if(explosions.get(i).isActive()) { explosions.get(i).decreaseLifeTime(); }
            else { explosions.remove(i); }
        }
    }
    private void deployEnemies() { 
        int deltaStartTime = (int)(currentTime-startTime)/1000;
        for(Enemy enemy:enemies){
            if(!enemy.ship.isActive() && enemy.delay <= deltaStartTime){
                enemy.activate();
            }
        }
    }
    private void enemyFire() {
        for(Enemy enemy:enemies){ 
            if(enemy.ship.isActive()){ fire(enemy.ship); }
        }
    }
    
    private void enemyMove() {
        for(Enemy enemy: enemies){
            if(enemy.ship.isActive()) { move(enemy.ship); }
        }
    }
    private void projectileMove() {
        for(Projectile p: projectiles) { 
            if(p.isActive()) { move(p); }
        }
    }

    private void checkOutcome() {
        
        if(player.getHealth()<=0)           {lost = true;}
        else if(enemies.isEmpty())          {victory = true;}
    }
    
    public boolean getVictory() { return victory; }
    public boolean getLost()    { return lost; }
}
