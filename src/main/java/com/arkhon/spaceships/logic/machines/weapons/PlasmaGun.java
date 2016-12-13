package com.arkhon.spaceships.logic.machines.weapons;

import com.arkhon.spaceships.gui.GameImage;
import static com.arkhon.spaceships.gui.GameImage.PLASMAGUN;
import static com.arkhon.spaceships.gui.GameImage.SMALLEXPLOSION;

public class PlasmaGun extends Weapon {

    public static final String WName = "PlasmaGun";
    public static final Weapon.WeaponTypes WType = Weapon.WeaponTypes.PLASMA;
    public static final int WDamage = 8;
    public static final int WSize = 6;
    public static final double WRateOfFire = 0.4;
    public static final int WPSpeed = 2;
    public static final GameImage WProjectileImage = PLASMAGUN;
    public static final GameImage[] WExplosionImage = {SMALLEXPLOSION};
    
    public PlasmaGun() {
        super(WName, WType, WDamage, WSize, WRateOfFire ,WPSpeed, WProjectileImage, WExplosionImage);
    }
} 