package net.bunten.enderscape;

import net.bunten.enderscape.client.LightingStyle;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnderscapeConfig {
    private final ModConfigSpec.Builder startup = new ModConfigSpec.Builder();
    private final ModConfigSpec.Builder client = new ModConfigSpec.Builder();
    
    public final ModConfigSpec.BooleanValue chorusFlowerHumming = client
            .define("chorusFlowerHumming", true);
    public final ModConfigSpec.BooleanValue chorusFlowerPollen = client.define("chorusFlowerPollen", true);
    public final ModConfigSpec.BooleanValue endPortalUpdateParticles = client.define("endPortalUpdateParticles", true);
    public final ModConfigSpec.BooleanValue endPortalUpdateTravelSound = client.define("endPortalUpdateTravelSound", true);
    
    public final ModConfigSpec.BooleanValue debugHudClientInfo = client.define("debugHudClientInfo", true);
    public final ModConfigSpec.BooleanValue debugHudEnabled = client.define("debugHudEnabled", false);
    public final ModConfigSpec.BooleanValue debugHudMusicInfo = client.define("debugHudMusicInfo", true);
    public final ModConfigSpec.BooleanValue debugHudPlayerInfo = client.define("debugHudPlayerInfo", true);
    public final ModConfigSpec.BooleanValue debugMagniaSproutHitboxes = client.define("debugMagniaSproutHitboxes", false);
    
    public final ModConfigSpec.BooleanValue elytraAddFovEffects = client.define("elytraAddFovEffects", true);
    public final ModConfigSpec.BooleanValue elytraAddGlidingSound = client.define("elytraAddGlidingSound", true);
    public final ModConfigSpec.IntValue elytraFovEffectIntensity = client.defineInRange("elytraFovEffectIntensity", 100, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.BooleanValue enderPearlAddParticles = client.define("enderPearlAddParticles", true);
    public final ModConfigSpec.BooleanValue mirrorScreenEffectEnabled = client.define("mirrorScreenEffectEnabled", true);
    public final ModConfigSpec.BooleanValue mirrorTooltipDisplayCoordinates = client.define("mirrorTooltipDisplayCoordinates", false);
    public final ModConfigSpec.BooleanValue mirrorTooltipDisplayDimension = client.define("mirrorTooltipDisplayDimension", true);
    public final ModConfigSpec.BooleanValue mirrorTooltipDisplayDistance = client.define("mirrorTooltipDisplayDistance", true);
    public final ModConfigSpec.BooleanValue mirrorTooltipEnabled = client.define("mirrorTooltipEnabled", true);
    public final ModConfigSpec.BooleanValue mirrorTooltipShiftToDisplay = client.define("mirrorTooltipShiftToDisplay", false);
    public final ModConfigSpec.BooleanValue nebuliteToolHudEnabled = client.define("nebuliteToolHudEnabled", true);
    public final ModConfigSpec.IntValue mirrorScreenEffectOverlayIntensity = client.defineInRange("mirrorScreenEffectOverlayIntensity", 50, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue mirrorScreenEffectVignetteIntensity = client.defineInRange("mirrorScreenEffectVignetteIntensity", 50, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue nebuliteToolHudOffset = client.defineInRange("nebuliteToolHudOffset", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue nebuliteToolHudOpacity = client.defineInRange("nebuliteToolHudOpacity", 100, Integer.MIN_VALUE, Integer.MAX_VALUE);

    public final ModConfigSpec.BooleanValue portalParticleEmissive = client.define("portalParticleEmissive", true);
    public final ModConfigSpec.BooleanValue endermiteEmissiveEyes = client.define("endermiteEmissiveEyes", true);
    public final ModConfigSpec.BooleanValue shulkerBulletLoopSound = client.define("shulkerBulletLoopSound", true);
    public final ModConfigSpec.BooleanValue endermanStaticOverlay = client.define("endermanStaticOverlay", true);
    public final ModConfigSpec.BooleanValue endermanStaticSound = client.define("endermanStaticSound", true);

    public final ModConfigSpec.BooleanValue skyboxAddDynamicFogDensity = client.define("skyboxAddDynamicFogDensity", true);
    public final ModConfigSpec.BooleanValue skyboxScalesBrightnessWithGamma = client.define("skyboxScalesBrightnessWithGamma", true);
    public final ModConfigSpec.BooleanValue skyboxUpdateEnabled = client.define("skyboxUpdateEnabled", true);
    public final ModConfigSpec.IntValue skyboxBrightnessScaleFactor = client.defineInRange("skyboxBrightnessScaleFactor", 40, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.EnumValue<LightingStyle> lightingStyle = client.defineEnum("lightingStyle", LightingStyle.IMPROVED);

    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultAdditions = startup.define("ambienceUpdateDefaultAdditions", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultFogColor = startup.define("ambienceUpdateDefaultFogColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultFoliageColor = startup.define("ambienceUpdateDefaultFoliageColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultGrassColor = startup.define("ambienceUpdateDefaultGrassColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultLoop = startup.define("ambienceUpdateDefaultLoop", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultMood = startup.define("ambienceUpdateDefaultMood", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultMusic = startup.define("ambienceUpdateDefaultMusic", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultParticles = startup.define("ambienceUpdateDefaultParticles", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultSkyColor = startup.define("ambienceUpdateDefaultSkyColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultWaterColor = startup.define("ambienceUpdateDefaultWaterColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultWaterFogColor = startup.define("ambienceUpdateDefaultWaterFogColor", true);
    public final ModConfigSpec.BooleanValue blockSoundsUpdateEndRods = startup.define("blockSoundsUpdateEndRods", true);
    public final ModConfigSpec.BooleanValue blockSoundsUpdateEndPortalFrame = startup.define("blockSoundsUpdateEndPortalFrame", true);
    public final ModConfigSpec.BooleanValue blockSoundsUpdateEndPortals = startup.define("blockSoundsUpdateEndPortals", true);
    public final ModConfigSpec.BooleanValue blockSoundsUpdateEndStone = startup.define("blockSoundsUpdateEndStone", true);
    public final ModConfigSpec.BooleanValue blockSoundsUpdateEndStoneBricks = startup.define("blockSoundsUpdateEndStoneBricks", true);
    public final ModConfigSpec.BooleanValue blockSoundsUpdateShulkerBoxes = startup.define("blockSoundsUpdateShulkerBoxes", true);
    public final ModConfigSpec.BooleanValue blockSoundsUpdateChorus = startup.define("blockSoundUpdateChorus", true);
    public final ModConfigSpec.BooleanValue blockSoundsUpdatePurpur = startup.define("blockSoundUpdatePurpur", true);
    
    public final ModConfigSpec.BooleanValue elytraAddOpenCloseSounds = startup.define("elytraAddOpenCloseSounds", true);
    public final ModConfigSpec.BooleanValue elytraSneakToStopGliding = startup.define("elytraSneakToStopGliding", true);
    public final ModConfigSpec.BooleanValue elytraUpdateEquipSound = startup.define("elytraUpdateEquipSound", true);
    public final ModConfigSpec.BooleanValue endermanStereoStareSound = startup.define("endermanStereoStareSound", true);
    public final ModConfigSpec.BooleanValue endermiteExpandHitRange = startup.define("endermiteExpandHitRange", true);
    public final ModConfigSpec.BooleanValue endermiteUpdateSounds = startup.define("endermiteUpdateSounds", true);
    public final ModConfigSpec.BooleanValue enderPearlBreakParticles = startup.define("enderPearlBreakParticles", true);
    public final ModConfigSpec.BooleanValue enderPearlUpdateTeleportSound = startup.define("enderPearlUpdateTeleportSound", true);
    public final ModConfigSpec.BooleanValue enderPearlUpdateThrowSound = startup.define("enderPearlUpdateThrowSound", true);
    
    public final ModConfigSpec.BooleanValue rubblemiteExpandHitRange = startup.define("rubblemiteExpandHitRange", true);
    public final ModConfigSpec.BooleanValue shulkerBulletEnforceOwnerLimit = startup.define("shulkerBulletEnforceOwnerLimit", true);
    public final ModConfigSpec.BooleanValue shulkerBulletRebalanceLevitation = startup.define("shulkerBulletRebalanceLevitation", true);
    public final ModConfigSpec.BooleanValue shulkerHurtByPiercing = startup.define("shulkerHurtByPiercing", true);
    public final ModConfigSpec.BooleanValue silverfishExpandHitRange = startup.define("silverfishExpandHitRange", true);
    
    public final ModConfigSpec.BooleanValue tridentsReturnFromVoid = startup.define("tridentsReturnFromVoid", true);
    public final ModConfigSpec.BooleanValue voidPoofParticlesUponDeath = startup.define("voidPoofParticlesUponDeath", true);
    public final ModConfigSpec.IntValue shulkerBulletEnforceCountLimit = startup.defineInRange("shulkerBulletEnforceCountLimit", 3, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue shulkerBulletEnforceDistanceLimit = startup.defineInRange("shulkerBulletEnforceDistanceLimit", 30, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue shulkerBulletEnforceTimeLimit = startup.defineInRange("shulkerBulletEnforceTimeLimit", 30, Integer.MIN_VALUE, Integer.MAX_VALUE);

    private final ModConfigSpec spec = startup.build();
    private final ModConfigSpec clientSpec = client.build();
    
    private static final EnderscapeConfig INSTANCE = new EnderscapeConfig();
    
    public static EnderscapeConfig getInstance() {
        return INSTANCE;
    }
    
    static void register(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.STARTUP, INSTANCE.spec);
        modContainer.registerConfig(ModConfig.Type.CLIENT, INSTANCE.clientSpec);
    }
}