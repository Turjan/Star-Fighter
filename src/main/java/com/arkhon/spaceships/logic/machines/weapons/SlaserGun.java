package com.arkhon.spaceships.logic.machines.weapons;

import com.arkhon.spaceships.gui.GameImage;
import static com.arkhon.spaceships.gui.GameImage.SLASERGUN;
import static com.arkhon.spaceships.gui.GameImage.SMALLEXPLOSION;


public class SlaserGun extends Weapon{

    public static final String WName = "SlaserGun";
    public static final WeaponTypes WType = WeaponTypes.LASER;
    public static final int WDamage = 5;
    public static final int WSize = 1;
    public static final double WRateOfFire = 0.5;
    public static final int WPSpeed = 3;
    public static final GameImage WProjectileImage = SLASERGUN;
    public static final GameImage[] WExplosionImage = {SMALLEXPLOSION};
    
    public SlaserGun() {
        super(WName, WType, WDamage, WSize, WRateOfFire ,WPSpeed, WProjectileImage, WExplosionImage);
    }
    
}
