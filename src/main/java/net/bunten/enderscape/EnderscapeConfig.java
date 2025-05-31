package net.bunten.enderscape;

import net.bunten.enderscape.client.LightingStyle;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class EnderscapeConfig {
    private final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultAdditions = BUILDER.define("ambienceUpdateDefaultAdditions", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultFogColor = BUILDER.define("ambienceUpdateDefaultFogColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultFoliageColor = BUILDER.define("ambienceUpdateDefaultFoliageColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultGrassColor = BUILDER.define("ambienceUpdateDefaultGrassColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultLoop = BUILDER.define("ambienceUpdateDefaultLoop", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultMood = BUILDER.define("ambienceUpdateDefaultMood", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultMusic = BUILDER.define("ambienceUpdateDefaultMusic", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultParticles = BUILDER.define("ambienceUpdateDefaultParticles", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultSkyColor = BUILDER.define("ambienceUpdateDefaultSkyColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultWaterColor = BUILDER.define("ambienceUpdateDefaultWaterColor", true);
    public final ModConfigSpec.BooleanValue ambienceUpdateDefaultWaterFogColor = BUILDER.define("ambienceUpdateDefaultWaterFogColor", true);
    public final ModConfigSpec.BooleanValue blockSoundsUpdateEndRods = BUILDER.define("blockSoundsUpdateEndRods", true);
    public final ModConfigSpec.BooleanValue blockSoundUpdateEndPortalFrame = BUILDER.define("blockSoundUpdateEndPortalFrame", true);
    public final ModConfigSpec.BooleanValue blockSoundUpdateEndPortals = BUILDER.define("blockSoundUpdateEndPortals", true);
    public final ModConfigSpec.BooleanValue blockSoundUpdateEndStone = BUILDER.define("blockSoundUpdateEndStone", true);
    public final ModConfigSpec.BooleanValue blockSoundUpdateEndStoneBricks = BUILDER.define("blockSoundUpdateEndStoneBricks", true);
    public final ModConfigSpec.BooleanValue blockSoundUpdateShulkerBoxes = BUILDER.define("blockSoundUpdateShulkerBoxes", true);
    public final ModConfigSpec.BooleanValue blocksSoundUpdateChorus = BUILDER.define("blocksSoundUpdateChorus", true);
    public final ModConfigSpec.BooleanValue blocksSoundUpdatePurpur = BUILDER.define("blocksSoundUpdatePurpur", true);
    public final ModConfigSpec.BooleanValue chorusFlowerHumming = BUILDER.define("chorusFlowerHumming", true);
    public final ModConfigSpec.BooleanValue chorusFlowerPollen = BUILDER.define("chorusFlowerPollen", true);
    public final ModConfigSpec.BooleanValue debugHudClientInfo = BUILDER.define("debugHudClientInfo", true);
    public final ModConfigSpec.BooleanValue debugHudEnabled = BUILDER.define("debugHudEnabled", false);
    public final ModConfigSpec.BooleanValue debugHudMusicInfo = BUILDER.define("debugHudMusicInfo", true);
    public final ModConfigSpec.BooleanValue debugHudPlayerInfo = BUILDER.define("debugHudPlayerInfo", true);
    public final ModConfigSpec.BooleanValue debugMagniaSproutHitboxes = BUILDER.define("debugMagniaSproutHitboxes", false);
    public final ModConfigSpec.BooleanValue elytraAddFovEffects = BUILDER.define("elytraAddFovEffects", true);
    public final ModConfigSpec.BooleanValue elytraAddGlidingSound = BUILDER.define("elytraAddGlidingSound", true);
    public final ModConfigSpec.BooleanValue elytraAddOpenCloseSounds = BUILDER.define("elytraAddOpenCloseSounds", true);
    public final ModConfigSpec.BooleanValue elytraSneakToStopGliding = BUILDER.define("elytraSneakToStopGliding", true);
    public final ModConfigSpec.BooleanValue elytraUpdateEquipSound = BUILDER.define("elytraUpdateEquipSound", true);
    public final ModConfigSpec.BooleanValue endermanStaticOverlay = BUILDER.define("endermanStaticOverlay", true);
    public final ModConfigSpec.BooleanValue endermanStaticSound = BUILDER.define("endermanStaticSound", true);
    public final ModConfigSpec.BooleanValue endermanStereoStareSound = BUILDER.define("endermanStereoStareSound", true);
    public final ModConfigSpec.BooleanValue endermiteEmissiveEyes = BUILDER.define("endermiteEmissiveEyes", true);
    public final ModConfigSpec.BooleanValue endermiteExpandHitRange = BUILDER.define("endermiteExpandHitRange", true);
    public final ModConfigSpec.BooleanValue endermiteUpdateSounds = BUILDER.define("endermiteUpdateSounds", true);
    public final ModConfigSpec.BooleanValue enderPearlAddParticles = BUILDER.define("enderPearlAddParticles", true);
    public final ModConfigSpec.BooleanValue enderPearlBreakParticles = BUILDER.define("enderPearlBreakParticles", true);
    public final ModConfigSpec.BooleanValue enderPearlUpdateTeleportSound = BUILDER.define("enderPearlUpdateTeleportSound", true);
    public final ModConfigSpec.BooleanValue enderPearlUpdateThrowSound = BUILDER.define("enderPearlUpdateThrowSound", true);
    public final ModConfigSpec.BooleanValue endPortalUpdateParticles = BUILDER.define("endPortalUpdateParticles", true);
    public final ModConfigSpec.BooleanValue endPortalUpdateTravelSound = BUILDER.define("endPortalUpdateTravelSound", true);
    public final ModConfigSpec.BooleanValue mirrorScreenEffectEnabled = BUILDER.define("mirrorScreenEffectEnabled", true);
    public final ModConfigSpec.BooleanValue mirrorTooltipDisplayCoordinates = BUILDER.define("mirrorTooltipDisplayCoordinates", false);
    public final ModConfigSpec.BooleanValue mirrorTooltipDisplayDimension = BUILDER.define("mirrorTooltipDisplayDimension", true);
    public final ModConfigSpec.BooleanValue mirrorTooltipDisplayDistance = BUILDER.define("mirrorTooltipDisplayDistance", true);
    public final ModConfigSpec.BooleanValue mirrorTooltipEnabled = BUILDER.define("mirrorTooltipEnabled", true);
    public final ModConfigSpec.BooleanValue mirrorTooltipShiftToDisplay = BUILDER.define("mirrorTooltipShiftToDisplay", false);
    public final ModConfigSpec.BooleanValue nebuliteToolHudEnabled = BUILDER.define("nebuliteToolHudEnabled", true);
    public final ModConfigSpec.BooleanValue portalParticleEmissive = BUILDER.define("portalParticleEmissive", true);
    public final ModConfigSpec.BooleanValue rubblemiteExpandHitRange = BUILDER.define("rubblemiteExpandHitRange", true);
    public final ModConfigSpec.BooleanValue shulkerBulletEnforceOwnerLimit = BUILDER.define("shulkerBulletEnforceOwnerLimit", true);
    public final ModConfigSpec.BooleanValue shulkerBulletLoopSound = BUILDER.define("shulkerBulletLoopSound", true);
    public final ModConfigSpec.BooleanValue shulkerBulletRebalanceLevitation = BUILDER.define("shulkerBulletRebalanceLevitation", true);
    public final ModConfigSpec.BooleanValue shulkerHurtByPiercing = BUILDER.define("shulkerHurtByPiercing", true);
    public final ModConfigSpec.BooleanValue silverfishExpandHitRange = BUILDER.define("silverfishExpandHitRange", true);
    public final ModConfigSpec.BooleanValue skyboxAddDynamicFogDensity = BUILDER.define("skyboxAddDynamicFogDensity", true);
    public final ModConfigSpec.BooleanValue skyboxScalesBrightnessWithGamma = BUILDER.define("skyboxScalesBrightnessWithGamma", true);
    public final ModConfigSpec.BooleanValue skyboxUpdateEnabled = BUILDER.define("skyboxUpdateEnabled", true);
    public final ModConfigSpec.BooleanValue tridentsReturnFromVoid = BUILDER.define("tridentsReturnFromVoid", true);
    public final ModConfigSpec.BooleanValue voidPoofParticlesUponDeath = BUILDER.define("voidPoofParticlesUponDeath", true);
    
    // Original mod does not define bounds here, so we do not enforce any
    public final ModConfigSpec.IntValue elytraFovEffectIntensity = BUILDER.defineInRange("elytraFovEffectIntensity", 100, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue mirrorScreenEffectOverlayIntensity = BUILDER.defineInRange("mirrorScreenEffectOverlayIntensity", 50, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue mirrorScreenEffectVignetteIntensity = BUILDER.defineInRange("mirrorScreenEffectVignetteIntensity", 50, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue nebuliteToolHudOffset = BUILDER.defineInRange("nebuliteToolHudOffset", 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue nebuliteToolHudOpacity = BUILDER.defineInRange("nebuliteToolHudOpacity", 100, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue shulkerBulletEnforceCountLimit = BUILDER.defineInRange("shulkerBulletEnforceCountLimit", 3, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue shulkerBulletEnforceDistanceLimit = BUILDER.defineInRange("shulkerBulletEnforceDistanceLimit", 30, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue shulkerBulletEnforceTimeLimit = BUILDER.defineInRange("shulkerBulletEnforceTimeLimit", 30, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.IntValue skyboxBrightnessScaleFactor = BUILDER.defineInRange("skyboxBrightnessScaleFactor", 40, Integer.MIN_VALUE, Integer.MAX_VALUE);
    public final ModConfigSpec.EnumValue<LightingStyle> lightingStyle = BUILDER.defineEnum("lightingStyle", LightingStyle.IMPROVED);

    private final ModConfigSpec SPEC = BUILDER.build();
    
    private static final EnderscapeConfig INSTANCE = new EnderscapeConfig();
    
    public static EnderscapeConfig getInstance() {
        return INSTANCE;
    }
    
    static void register(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.STARTUP, INSTANCE.SPEC);
    }
}