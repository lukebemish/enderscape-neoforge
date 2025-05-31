package net.bunten.enderscape;

import com.google.common.reflect.Reflection;
import net.bunten.enderscape.datagen.EnderscapeBiomeModifiers;
import net.bunten.enderscape.registry.*;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import terrablender.api.EndBiomeRegistry;

import java.util.function.Supplier;

@Mod(Enderscape.MOD_ID)
public class Enderscape {

    public static final String MOD_ID = "enderscape";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final boolean IS_DEBUG = !FMLLoader.isProduction();

    public static final ResourceKey<Structure> END_CITY_RESOURCE_KEY = ResourceKey.create(Registries.STRUCTURE, Enderscape.id("end_city"));

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static SoundEvent registerSoundEvent(String name) {
        ResourceLocation id = id(name);
        var soundEvent = SoundEvent.createVariableRangeEvent(id);
        RegistryHelper.register(BuiltInRegistries.SOUND_EVENT, id, () -> soundEvent);
        return soundEvent;
    }

    public static Holder<SoundEvent> registerSoundEventHolder(String name) {
        ResourceLocation location = id(name);
        return RegistryHelper.registerForHolder(BuiltInRegistries.SOUND_EVENT, location, () -> SoundEvent.createVariableRangeEvent(location));
    }

    private static void addBiomeForVanillaWorldgen(ResourceKey<Biome> key, double doubleWeight) {
        int weight = (int) Math.round(doubleWeight * 10);
        EndBiomeRegistry.registerHighlandsBiome(key, weight);
        EndBiomeRegistry.registerMidlandsBiome(key, weight);
        EndBiomeRegistry.registerEdgeBiome(key, weight);
    }

    public Enderscape(IEventBus modBus, ModContainer modContainer) {
        EnderscapeConfig.register(modContainer);
        
        Reflection.initialize(
                EnderscapeItems.class,
                EnderscapeBlocks.class,
                EnderscapeFeatures.class,
                EnderscapeBlockEntities.class,
                EnderscapeBiomeSounds.class,
                EnderscapeBlockSounds.class,
                EnderscapeEntitySounds.class,
                EnderscapeItemSounds.class,
                EnderscapeMusic.class,
                EnderscapeSoundTypes.class,
                EnderscapePoi.class,
                EnderscapeParticles.class,
                EnderscapeEnchantmentEffectComponents.class,
                EnderscapeMobEffects.class,
                EnderscapeAssetModifications.class,
                EnderscapeEntities.class,
                EnderscapeSubEntityPredicates.class,
                EnderscapeStats.class,
                EnderscapeCriteria.class,
                EnderscapePotions.class,
                EnderscapeServerNetworking.class,
                EnderscapeModifications.class,
                EnderscapeSoundTypeOverrides.class,
                EnderscapeCreativeModeTab.class,
                EnderscapeDataComponents.class,
                EnderscapeDensityFunctionTypes.class,
                EnderscapeStructureMusic.class,
                EnderscapeBiomeModifiers.class,
                EnderscapeDataAttachments.class
        );

        addBiomeForVanillaWorldgen(EnderscapeBiomes.MAGNIA_CRAGS, 0.8);
        addBiomeForVanillaWorldgen(EnderscapeBiomes.VEILED_WOODLANDS, 0.7);
        addBiomeForVanillaWorldgen(EnderscapeBiomes.CORRUPT_BARRENS, 0.5);
        addBiomeForVanillaWorldgen(EnderscapeBiomes.CELESTIAL_GROVE, 0.3);

        modBus.addListener(AddPackFindersEvent.class, event -> {
            registerClientResourcePack(event, Enderscape.id("fix_levitation_advancement"), Component.translatable("pack.enderscape.fix_levitation_advancement"));
            registerClientResourcePack(event, Enderscape.id("fix_vanilla_recipes"), Component.translatable("pack.enderscape.fix_vanilla_recipes"));
            registerClientResourcePack(event, Enderscape.id("new_end_cities"), Component.translatable("pack.enderscape.new_end_cities"));
            registerClientResourcePack(event, Enderscape.id("new_terrain"), Component.translatable("pack.enderscape.new_terrain"));
        });

        LOGGER.info("Enderscape initialized!");
    }
    
    private void registerClientResourcePack(AddPackFindersEvent event, ResourceLocation id, Component name) {
        event.addPackFinders(id.withPrefix("resourcepacks"), PackType.CLIENT_RESOURCES, name, PackSource.BUILT_IN, true, Pack.Position.TOP);
    }
}