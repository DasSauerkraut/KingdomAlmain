package data.scripts.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class AlmainOnagerCannon implements EveryFrameWeaponEffectPlugin {

    private static final Color MUZZLE_FLASH_COLOR_BRIGHT = new Color(255, 165, 158, 255);
    private static final Color MUZZLE_FLASH_COLOR = new Color(255, 0, 0, 255);

    private final String CHARGE_SOUND_ID = "almain_onager_charge";
    private final String FIRE_SOUND_ID = "almain_onager_fire";

    private boolean hasFired = false;

    @Override
    public void advance(float amount, CombatEngineAPI engine, WeaponAPI weapon) {
        // Don't bother with any unecessary checks
        if (weapon.getShip() == null) {
            return;
        }

        ShipAPI ship = weapon.getShip();

        //Plays charge sound and spawn some charge-y particles
        if (!hasFired && !engine.isPaused() && weapon.getChargeLevel() > 0f) {
            Global.getSoundPlayer().playLoop(CHARGE_SOUND_ID, ship, 1f, (0.5f + (weapon.getChargeLevel() * 0.5f)), weapon.getLocation(), new Vector2f(0f, 0f));
        }

        //Actually fire the weapon!
        if (weapon.getChargeLevel() >= 1f && !hasFired) {
            hasFired = true;
            //For spawning the sounds at the end, middle and start of the beam
            Global.getSoundPlayer().playSound(FIRE_SOUND_ID, 1f, 1.25f, weapon.getLocation(), new Vector2f(0f, 0f));

            //Muzzle flash
            engine.addHitParticle(weapon.getLocation(), ship.getVelocity(), 325f, 0.22f, 1.9f, MUZZLE_FLASH_COLOR_BRIGHT);
            engine.addHitParticle(weapon.getLocation(), ship.getVelocity(), 850f, 0.13f, 2f, MUZZLE_FLASH_COLOR);
        }

        if (hasFired && weapon.getChargeLevel() == 0f) {
            hasFired = false;
        }
    }
}