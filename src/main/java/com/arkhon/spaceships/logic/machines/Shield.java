package com.arkhon.spaceships.logic.machines;

public class Shield {
    
    private double health;
    public final double rechargeRate;
    public final int size;

    public Shield(double health, double rechargeRate, int size) {
        this.health = health;
        this.rechargeRate = rechargeRate;
        this.size = size;
    }

    public double getHealth()            { return health; }
    public void setHealth(double health) { this.health = health; }
}
