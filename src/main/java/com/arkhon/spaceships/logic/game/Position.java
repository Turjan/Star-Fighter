package com.arkhon.spaceships.logic.game;

public class Position {
    
    public final int x;
    public final int y;
    
    public Position(int x, int y){
    
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = (x + y)*7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)                    { return false; }
        if (getClass() != obj.getClass())   { return false; }
        
        final Position other = (Position) obj;
        return (this.x == other.x || this.y == other.y);
    }

    @Override
    public String toString() {
        return "Position{" + "x=" + x + ", y=" + y + '}';
    }

    
    
    
}
