package data.scripts.world.almain;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.SectorGeneratorPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.shared.SharedData;
import data.scripts.world.almain.telum.Telum;

public class AlmainGen implements SectorGeneratorPlugin {

  public static void initFactionRelationships(SectorAPI sector) {

    SharedData.getData().getPersonBountyEventData().addParticipatingFaction("almain");

    FactionAPI hegemony = sector.getFaction(Factions.HEGEMONY);
    FactionAPI diktat = sector.getFaction(Factions.DIKTAT);
    FactionAPI pirates = sector.getFaction(Factions.PIRATES);
    FactionAPI kol = sector.getFaction(Factions.KOL);
    FactionAPI church = sector.getFaction(Factions.LUDDIC_CHURCH);
    FactionAPI path = sector.getFaction(Factions.LUDDIC_PATH);
    FactionAPI almain = sector.getFaction("almain");

    almain.setRelationship(path.getId(), RepLevel.VENGEFUL);
    almain.setRelationship(hegemony.getId(), RepLevel.HOSTILE);
    almain.setRelationship(pirates.getId(), RepLevel.HOSTILE);
    almain.setRelationship(church.getId(), RepLevel.VENGEFUL);
    almain.setRelationship(kol.getId(), RepLevel.VENGEFUL);
    almain.setRelationship(diktat.getId(), RepLevel.FRIENDLY);
  }

  @Override
  public void generate(SectorAPI sector) {
    SharedData.getData().getPersonBountyEventData().addParticipatingFaction("almain");

    initFactionRelationships(sector);

    new Telum().generate(sector);
  }

}


