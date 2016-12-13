package com.arkhon.spaceships.logic.machines.weapons;

import com.arkhon.spaceships.gui.GameImage;
import static com.arkhon.spaceships.gui.GameImage.CONDORGUN;
import static com.arkhon.spaceships.gui.GameImage.SMALLEXPLOSION;

public class CondorGun extends Weapon {

    public static final String WName = "CondorGun";
    public static final WeaponTypes WType = WeaponTypes.KINETIC;
    public static final int WDamage = 4;
    public static final int WSize = 4;
    public static final double WRateOfFire = 3;
    public static final int WPSpeed = 2;
    public static final GameImage WProjectileImage = CONDORGUN;
    public static final GameImage[] WExplosionImage = {SMALLEXPLOSION};
    
    public CondorGun() {
        super(WName, WType, WDamage, WSize, WRateOfFire ,WPSpeed, WProjectileImage, WExplosionImage);
    }
    
}
