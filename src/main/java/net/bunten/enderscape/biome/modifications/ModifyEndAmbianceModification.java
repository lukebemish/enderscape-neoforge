package net.bunten.enderscape.biome.modifications;

import com.mojang.serialization.MapCodec;
import net.bunten.enderscape.EnderscapeConfig;
import net.bunten.enderscape.registry.EnderscapeBiomeSounds;
import net.bunten.enderscape.registry.EnderscapeBiomes;
import net.bunten.enderscape.registry.EnderscapeParticles;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public final class ModifyEndAmbianceModification implements BiomeModifier {
    private ModifyEndAmbianceModification() {}
    
    public static final ModifyEndAmbianceModification INSTANCE = new ModifyEndAmbianceModification();
    
    public static final MapCodec<ModifyEndAmbianceModification> CODEC = MapCodec.unit(INSTANCE);

    private static final EnderscapeConfig CONFIG = EnderscapeConfig.getInstance();
    
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (CONFIG.ambienceUpdateDefaultMusic.get()) builder.getSpecialEffects().backgroundMusic(new Music(EnderscapeBiomeSounds.DEFAULT_END.music(), 12000, 24000, false));

        if (CONFIG.ambienceUpdateDefaultLoop.get()) builder.getSpecialEffects().ambientLoopSound(EnderscapeBiomeSounds.DEFAULT_END.loop());
        if (CONFIG.ambienceUpdateDefaultAdditions.get()) builder.getSpecialEffects().ambientAdditionsSound(new AmbientAdditionsSettings(EnderscapeBiomeSounds.DEFAULT_END.additions(), 0.00075));
        if (CONFIG.ambienceUpdateDefaultMood.get()) builder.getSpecialEffects().ambientMoodSound(new AmbientMoodSettings(EnderscapeBiomeSounds.DEFAULT_END.mood(), 6000, 8, 2));
        if (CONFIG.ambienceUpdateDefaultParticles.get()) builder.getSpecialEffects().ambientParticle(new AmbientParticleSettings(EnderscapeParticles.VOID_STARS.get(), 0.003F));

        if (CONFIG.ambienceUpdateDefaultSkyColor.get()) builder.getSpecialEffects().skyColor(EnderscapeBiomes.DEFAULT_SKY_COLOR);
        if (CONFIG.ambienceUpdateDefaultFogColor.get()) builder.getSpecialEffects().fogColor(EnderscapeBiomes.DEFAULT_FOG_COLOR);

        if (CONFIG.ambienceUpdateDefaultGrassColor.get()) builder.getSpecialEffects().grassColorOverride(EnderscapeBiomes.DEFAULT_GRASS_COLOR);
        if (CONFIG.ambienceUpdateDefaultFoliageColor.get()) builder.getSpecialEffects().foliageColorOverride(EnderscapeBiomes.DEFAULT_FOLIAGE_COLOR);

        if (CONFIG.ambienceUpdateDefaultWaterColor.get()) builder.getSpecialEffects().waterColor(EnderscapeBiomes.DEFAULT_WATER_COLOR);
        if (CONFIG.ambienceUpdateDefaultWaterFogColor.get()) builder.getSpecialEffects().waterFogColor(EnderscapeBiomes.DEFAULT_WATER_FOG_COLOR);
    }

    @Override
    public MapCodec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
