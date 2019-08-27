package data.scripts.weapons;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.*;
import com.fs.starfarer.api.graphics.SpriteAPI;
import data.scripts.plugins.MagicTrailPlugin;
import org.lazywizard.lazylib.MathUtils;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class almainOnagerCannon implements {
    private final String CHARGE_SOUND_ID = "almain_onager_charge";
    private boolean runOnce = false;

    @Override
    public void advance(float amount, CombatEngineAPI engine, DamagingProjectileAPI shell) {
        // Don't bother with any unecessary checks
        if (shell.getWeapon().getShip() == null) {
            return;
        }

        WeaponAPI weapon = shell.getWeapon();
        ShipAPI ship = weapon.getShip();

        if(!runOnce && !engine.isPaused()) {
            Global.getSoundPlayer().playLoop(CHARGE_SOUND_ID, ship, (0.65f + weapon.getChargeLevel()*2f), (0.5f + (weapon.getChargeLevel() * 0.5f)), beam.getFrom(), new Vector2f(0f, 0f));
        }
    }
}
