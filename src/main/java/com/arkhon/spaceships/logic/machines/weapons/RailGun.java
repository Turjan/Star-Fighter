package com.arkhon.spaceships.logic.machines.weapons;

import com.arkhon.spaceships.gui.GameImage;
import static com.arkhon.spaceships.gui.GameImage.RAILGUN;
import static com.arkhon.spaceships.gui.GameImage.SMALLEXPLOSION;

public class RailGun extends Weapon{

    public static final String WName = "Railgun";
    public static final WeaponTypes WType = WeaponTypes.KINETIC;
    public static final int WDamage = 5;
    public static final int WSize = 6;
    public static final double WRateOfFire = 10;
    public static final int WPSpeed = 3;
    public static final GameImage WProjectileImage = RAILGUN;
    public static final GameImage[] WExplosionImage = {SMALLEXPLOSION};
    
    public RailGun() {
        super(WName, WType, WDamage, WSize, WRateOfFire ,WPSpeed, WProjectileImage, WExplosionImage);
    }
    
}
