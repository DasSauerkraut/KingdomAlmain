package data.scripts;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.CampaignPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.world.almain.AlmainGen;
import exerelin.campaign.SectorManager;

public class AlmainModPlugin extends BaseModPlugin {
  public static boolean isExerelin = false;

  private static void initAlmain() {
    if (isExerelin && !SectorManager.getCorvusMode()) {
      return;
    }
    new AlmainGen().generate(Global.getSector());
  }

  @Override
  public void onApplicationLoad() {
    isExerelin = Global.getSettings().getModManager().isModEnabled("nexerelin");
  }

  @Override
  public void onNewGame() {
    initAlmain();
  }
}

