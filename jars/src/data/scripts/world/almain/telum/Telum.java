package data.scripts.world.almain.telum;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.JumpPointAPI;
import com.fs.starfarer.api.campaign.OrbitAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import data.scripts.world.almain.addMarketplace;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

public class Telum {
  public void generate(SectorAPI sector){
    StarSystemAPI system = sector.createStarSystem("Telum");
    system.getLocation().set(18000, -18600);
    system.setBackgroundTextureFilename("graphics/almain/backgrounds/STOLENREPLACEME.jpg");

    PlanetAPI telum = system.initStar("telum", "star_yellow", 450f, 500f); //0.9 solar masses

    // SectorEntityToken telum_nebula = Misc.addNebulaFromPNG("data/campaign/terrain/")
    PlanetAPI almainen = system.addPlanet("almainen", telum, "Almainen", "terran", 300, 140, 3400, 215);
    almainen.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "aurorae"));
    almainen.getSpec().setGlowColor(new Color(255,255,255,255));
    almainen.getSpec().setUseReverseLightForGlow(true);
    almainen.applySpecChanges();
    almainen.setInteractionImage("illustrations", "terran_orbit");
    almainen.setCustomDescriptionId("almain_almainenplanet");

    PlanetAPI cadere = system.addPlanet("lodestone", almainen,"Cadere", "barren3", 30, 50, 500, 25); // 0.0025 AU
    cadere.setCustomDescriptionId("almain_cadere");
    cadere.setInteractionImage("illustrations", "space_bar");
    cadere.getSpec().setRotation(5f); // 5 degrees/second = 7.2 days/revolution
    cadere.getSpec().setGlowTexture(Global.getSettings().getSpriteName("hab_glows", "sindria"));
    cadere.getSpec().setGlowColor(new Color(255, 255, 255, 255));
    cadere.getSpec().setUseReverseLightForGlow(true);
    cadere.applySpecChanges();

    SectorEntityToken AlmainStation = system.addCustomEntity("telum_almain_station", "The Royal City of Almainen", "station_side06", "almain");
    AlmainStation.setCircularOrbitPointingDown(cadere, 270, 75,-7.2f);
    AlmainStation.setInteractionImage("illustrations", "city_from_above");

    SectorEntityToken relay = system.addCustomEntity("telum_relay", "Telum Relay", "comm_relay", "almain");
    relay.setCircularOrbit(telum, 240, 3650, 240);

    PlanetAPI calyx = system.addPlanet("calyx", telum, "Calyx", "toxic", 100, 130, 6500, 360);
    system.addAsteroidBelt(telum, 70, 6500, 128, 448, 470);

    JumpPointAPI jumpPoint = Global.getFactory().createJumpPoint("almain_jp", "The Royal Road");
    OrbitAPI orbit = Global.getFactory().createCircularOrbit(almainen, 90, 550, 25);
    jumpPoint.setOrbit(orbit);
    jumpPoint.setRelatedPlanet(cadere);
    jumpPoint.setStandardWormholeToHyperspaceVisual();
    system.addEntity(jumpPoint);

    addMarketplace.addMarketplace("almain", almainen,
            new ArrayList<>(Arrays.asList(AlmainStation, cadere)),
            "Almainen",
            6,
            new ArrayList<>(Arrays.asList(
                    Conditions.FARMLAND_BOUNTIFUL,
                    Conditions.HABITABLE,
                    Conditions.POPULATION_7,
                    Conditions.REGIONAL_CAPITAL,
                    Conditions.VICE_DEMAND,
                    Conditions.RURAL_POLITY,
                    Conditions.WATER,
                    Conditions.MILD_CLIMATE
            )),
            new ArrayList<>(Arrays.asList(
                    Industries.POPULATION,
                    Industries.GROUNDDEFENSES,
                    Industries.FARMING,
                    Industries.STARFORTRESS_MID,
                    Industries.MEGAPORT,
                    Industries.HIGHCOMMAND
            )),
            new ArrayList<>(Arrays.asList(
                    Submarkets.SUBMARKET_STORAGE,
                    Submarkets.SUBMARKET_OPEN,
                    Submarkets.GENERIC_MILITARY,
                    Submarkets.SUBMARKET_BLACK
            )),
            0.3f
    );

    float radiusAfter = StarSystemGenerator.addOrbitingEntities(system, telum, StarAge.AVERAGE, 2, 5, 14200, 3, true);

    system.autogenerateHyperspaceJumpPoints(true, true);
  }

  void cleanup(StarSystemAPI system){
    HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
    NebulaEditor editor = new NebulaEditor(plugin);
    float minRadius = plugin.getTileSize() * 2f;

    float radius = system.getMaxRadiusInHyperspace();
    editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius * 0.5f, 0, 360f);
    editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);
  }
}