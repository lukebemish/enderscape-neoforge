package net.bunten.enderscape.registry;

import net.bunten.enderscape.biome.util.SkyParameters;
import net.bunten.enderscape.util.RGBA;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

import static net.bunten.enderscape.registry.EnderscapeBiomes.*;

public class EnderscapeSkyParameters {

    public static final List<ResourceKey<SkyParameters>> SKY_PARAMETERS = new ArrayList<>();

    public void bootstrap(BootstrapContext<SkyParameters> context) {
        register(context, CELESTIAL_GROVE, 0x875643, DEFAULT_NEBULA_ALPHA * 1.15F, 0xFFA589, DEFAULT_STAR_ALPHA * 1.4F, 2.0F, 1.0F);
        register(context, CORRUPT_BARRENS, RGBA.darkenColor(DEFAULT_NEBULA_COLOR, CORRUPT_BARRENS_DARKENING_FACTOR), DEFAULT_NEBULA_ALPHA, RGBA.darkenColor(DEFAULT_NEBULA_COLOR, CORRUPT_BARRENS_DARKENING_FACTOR), DEFAULT_STAR_ALPHA, 4.0F, 1.25F);

        register(context, MAGNIA_CRAGS, 0x96AD9E, DEFAULT_NEBULA_ALPHA * 0.5F, 0x89FFD5, DEFAULT_STAR_ALPHA * 0.85F, 1.0F, 1.0F);
        register(context, VEILED_WOODLANDS, 0x969BAA, DEFAULT_NEBULA_ALPHA * 0.7F, 0xB493FF, DEFAULT_STAR_ALPHA, 4.0F, 1.0F);

        register(context, VOID_DEPTHS, RGBA.darkenColor(DEFAULT_NEBULA_COLOR, VOID_BIOMES_DARKENING_FACTOR), DEFAULT_NEBULA_ALPHA, RGBA.darkenColor(DEFAULT_STAR_COLOR, VOID_BIOMES_DARKENING_FACTOR), DEFAULT_STAR_ALPHA, 4.0F, 2.0F);
        register(context, VOID_SKIES, RGBA.darkenColor(DEFAULT_NEBULA_COLOR, VOID_BIOMES_DARKENING_FACTOR), DEFAULT_NEBULA_ALPHA, RGBA.darkenColor(DEFAULT_STAR_COLOR, VOID_BIOMES_DARKENING_FACTOR), DEFAULT_STAR_ALPHA, 4.0F, 2.0F);
        register(context, VOID_SKY_ISLANDS, RGBA.darkenColor(DEFAULT_NEBULA_COLOR, VOID_BIOMES_DARKENING_FACTOR), DEFAULT_NEBULA_ALPHA, RGBA.darkenColor(DEFAULT_STAR_COLOR, VOID_BIOMES_DARKENING_FACTOR), DEFAULT_STAR_ALPHA, 4.0F, 2.0F);}

    private static void register(BootstrapContext<SkyParameters> context, ResourceKey<Biome> biome, int nebulaColor, float nebulaAlpha, int starColor, float starAlpha, float fogStartDensity, float fogEndDensity) {
        context.register(register(biome.location()), new SkyParameters(biome.location(), nebulaColor, nebulaAlpha, starColor, starAlpha, fogStartDensity, fogEndDensity));
    }

    private static ResourceKey<SkyParameters> register(ResourceLocation location) {
        ResourceKey<SkyParameters> key = ResourceKey.create(EnderscapeRegistries.SKY_PARAMETERS_KEY, location);
        SKY_PARAMETERS.add(key);
        return key;
    }
}