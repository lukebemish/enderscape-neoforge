package net.bunten.enderscape.registry;

import net.bunten.enderscape.Enderscape;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.data.worldgen.placement.PlacementUtils.FULL_RANGE;
import static net.minecraft.world.level.block.Blocks.AIR;
import static net.minecraft.world.level.block.Blocks.END_STONE;
import static net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate.*;

public class EnderscapePlacedFeatures {
    public EnderscapePlacedFeatures() {
        RegistryHelper.checkAllReady();
    }

    public static final List<ResourceKey<PlacedFeature>> PLACED_FEATURES = new ArrayList<>();

    public static final ResourceKey<PlacedFeature> BLINKLIGHT_VINES = register("blinklight_vines");
    public static final ResourceKey<PlacedFeature> BULB_FLOWER = register("bulb_flower_patches");
    public static final ResourceKey<PlacedFeature> CEILING_NEBULITE_ORE = register("ceiling_nebulite_ore");
    public static final ResourceKey<PlacedFeature> CELESTIAL_GROVE_VEGETATION = register("celestial_grove_vegetation");
    public static final ResourceKey<PlacedFeature> CELESTIAL_GROWTH = register("celestial_growth");
    public static final ResourceKey<PlacedFeature> CHORUS_PLANTS = register("chorus_plants");
    public static final ResourceKey<PlacedFeature> CHORUS_SPROUTS = register("chorus_sprouts");
    public static final ResourceKey<PlacedFeature> COMMMON_CEILING_NEBULITE_ORE = register("commmon_ceiling_nebulite_ore");
    public static final ResourceKey<PlacedFeature> COMMON_CHORUS_SPROUTS = register("common_chorus_sprouts");
    public static final ResourceKey<PlacedFeature> COMMON_DRY_END_GROWTH = register("common_dry_end_growth");
    public static final ResourceKey<PlacedFeature> CORRUPT_BARRENS_VEGETATION = register("corrupt_barrens_vegetation");
    public static final ResourceKey<PlacedFeature> DOWNWARD_CORRUPT_GROWTH = register("downward_corrupt_growth");
    public static final ResourceKey<PlacedFeature> DOWNWARD_REPULSIVE_MAGNIA_SPROUTS = register("downward_repulsive_magnia_sprouts");
    public static final ResourceKey<PlacedFeature> DOWNWARD_TALL_CORRUPT_GROWTH = register("downward_tall_corrupt_growth");
    public static final ResourceKey<PlacedFeature> DRY_END_GROWTH = register("dry_end_growth");
    public static final ResourceKey<PlacedFeature> KURODITE = register("kurodite");
    public static final ResourceKey<PlacedFeature> LARGE_CELESTIAL_CHANTERELLES = register("large_celestial_chanterelles");
    public static final ResourceKey<PlacedFeature> LARGE_MURUBLIGHT_CHANTERELLES = register("large_murublight_chanterelles");
    public static final ResourceKey<PlacedFeature> MAGNIA_ARCH = register("magnia_arch");
    public static final ResourceKey<PlacedFeature> MAGNIA_TOWER = register("magnia_tower");
    public static final ResourceKey<PlacedFeature> MIRESTONE_BLOBS = register("mirestone_blobs");
    public static final ResourceKey<PlacedFeature> MIRESTONE_PILLARS = register("mirestone_pillars");
    public static final ResourceKey<PlacedFeature> MURUBLIGHT_SHELF = register("murublight_shelf");
    public static final ResourceKey<PlacedFeature> NEBULITE_ORE = register("nebulite_ore");
    public static final ResourceKey<PlacedFeature> SCATTERED_SHADOLINE_ORE = register("scattered_shadoline_ore");
    public static final ResourceKey<PlacedFeature> SHADOLINE_ORE = register("shadoline_ore");
    public static final ResourceKey<PlacedFeature> SMALL_ISLANDS = register("sky_islands");
    public static final ResourceKey<PlacedFeature> SMALL_SKY_ISLANDS = register("small_sky_islands");
    public static final ResourceKey<PlacedFeature> UNCOMMON_CHORUS_PLANTS = register("uncommon_chorus_plants");
    public static final ResourceKey<PlacedFeature> UNCOMMON_CHORUS_SPROUTS = register("uncommon_chorus_sprouts");
    public static final ResourceKey<PlacedFeature> UNCOMMON_MURUBLIGHT_SHELF = register("uncommon_murublight_shelf");
    public static final ResourceKey<PlacedFeature> UPWARD_ALLURING_MAGNIA_SPROUTS = register("upward_alluring_magnia_sprouts");
    public static final ResourceKey<PlacedFeature> UPWARD_CORRUPT_GROWTH = register("upward_corrupt_growth");
    public static final ResourceKey<PlacedFeature> UPWARD_TALL_CORRUPT_GROWTH = register("upward_tall_corrupt_growth");
    public static final ResourceKey<PlacedFeature> VEILED_TREES = register("veiled_trees");
    public static final ResourceKey<PlacedFeature> VEILED_WOODLANDS_VEGETATION = register("veiled_woodlands_vegetation");
    public static final ResourceKey<PlacedFeature> VERADITE = register("veradite");
    public static final ResourceKey<PlacedFeature> VOID_SHALE = register("void_shale");
    public static final ResourceKey<PlacedFeature> VOID_SHALE_BLOBS = register("void_shale_blobs");
    public static final ResourceKey<PlacedFeature> WISP_FLOWER_PATCHES = register("wisp_flower_patches");

    public final PlacementModifier aboveDepthsRange = HeightRangePlacement.uniform(VerticalAnchor.absolute(20), VerticalAnchor.top());
    
    public void bootstrap(BootstrapContext<PlacedFeature> context) {
        registerVeiledWoodlands(context);
        registerMagniaCrags(context);
        registerCelestialGrove(context);
        registerCorruptBarrens(context);

        PlacementUtils.register(context, CHORUS_PLANTS, get(context, EndFeatures.CHORUS_PLANT),
                FULL_RANGE,
                CountOnEveryLayerPlacement.of(UniformInt.of(2, 4)),
                BlockPredicateFilter.forPredicate(
                        allOf(
                                matchesBlocks(new Vec3i(0, 0, 1), AIR),
                                matchesBlocks(new Vec3i(0, 0, -1), AIR),
                                matchesBlocks(new Vec3i(1, 0, 0), AIR),
                                matchesBlocks(new Vec3i(-1, 0, 0), AIR),
                                matchesBlocks(new Vec3i(0, 3, 1), AIR),
                                matchesBlocks(new Vec3i(0, 4, 1), AIR)
                        )
                ),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, UNCOMMON_CHORUS_PLANTS, get(context, EndFeatures.CHORUS_PLANT),
                FULL_RANGE,
                CountOnEveryLayerPlacement.of(UniformInt.of(1, 3)),
                RarityFilter.onAverageOnceEvery(4),
                BlockPredicateFilter.forPredicate(
                        allOf(
                                matchesBlocks(new Vec3i(0, 0, 1), AIR),
                                matchesBlocks(new Vec3i(0, 0, -1), AIR),
                                matchesBlocks(new Vec3i(1, 0, 0), AIR),
                                matchesBlocks(new Vec3i(-1, 0, 0), AIR),
                                matchesBlocks(new Vec3i(0, 3, 1), AIR),
                                matchesBlocks(new Vec3i(0, 4, 1), AIR)
                        )
                ),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, DRY_END_GROWTH, get(context, EnderscapeConfiguredFeatures.DRY_END_GROWTH),
                FULL_RANGE,
                CountOnEveryLayerPlacement.of(UniformInt.of(1, 3)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(END_STONE), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, COMMON_DRY_END_GROWTH, get(context, EnderscapeConfiguredFeatures.DRY_END_GROWTH),
                FULL_RANGE,
                CountOnEveryLayerPlacement.of(UniformInt.of(3, 20)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(END_STONE), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, CHORUS_SPROUTS, get(context, EnderscapeConfiguredFeatures.CHORUS_SPROUTS),
                FULL_RANGE,
                CountOnEveryLayerPlacement.of(UniformInt.of(1, 3)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(END_STONE), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, COMMON_CHORUS_SPROUTS, get(context, EnderscapeConfiguredFeatures.CHORUS_SPROUTS),
                FULL_RANGE,
                CountOnEveryLayerPlacement.of(UniformInt.of(4, 8)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(END_STONE), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, UNCOMMON_CHORUS_SPROUTS, get(context, EnderscapeConfiguredFeatures.CHORUS_SPROUTS),
                FULL_RANGE,
                CountOnEveryLayerPlacement.of(UniformInt.of(1, 3)),
                RarityFilter.onAverageOnceEvery(3),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(END_STONE), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, MURUBLIGHT_SHELF, get(context, EnderscapeConfiguredFeatures.MURUBLIGHT_SHELF),
                FULL_RANGE,
                CountPlacement.of(30),
                BiomeFilter.biome(),
                InSquarePlacement.spread()
        );

        PlacementUtils.register(context, NEBULITE_ORE, get(context, EnderscapeConfiguredFeatures.NEBULITE_ORE),
                FULL_RANGE,
                BiomeFilter.biome(),
                CountPlacement.of(5),
                InSquarePlacement.spread()
        );

        PlacementUtils.register(context, CEILING_NEBULITE_ORE, get(context, EnderscapeConfiguredFeatures.CEILING_NEBULITE_ORE),
                FULL_RANGE,
                CountPlacement.of(4),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, COMMMON_CEILING_NEBULITE_ORE, get(context, EnderscapeConfiguredFeatures.CEILING_NEBULITE_ORE),
                FULL_RANGE,
                CountPlacement.of(9),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, SHADOLINE_ORE, get(context, EnderscapeConfiguredFeatures.SHADOLINE_ORE),
                FULL_RANGE,
                CountPlacement.of(12),
                BiomeFilter.biome(),
                InSquarePlacement.spread()
        );

        PlacementUtils.register(context, SCATTERED_SHADOLINE_ORE, get(context, EnderscapeConfiguredFeatures.SCATTERED_SHADOLINE_ORE),
                FULL_RANGE,
                CountPlacement.of(6),
                BiomeFilter.biome(),
                InSquarePlacement.spread()
        );

        PlacementUtils.register(context, VERADITE, get(context, EnderscapeConfiguredFeatures.VERADITE),
                FULL_RANGE,
                CountPlacement.of(48),
                BiomeFilter.biome(),
                InSquarePlacement.spread()
        );

        PlacementUtils.register(context, VOID_SHALE, get(context, EnderscapeConfiguredFeatures.VOID_SHALE),
                FULL_RANGE,
                RarityFilter.onAverageOnceEvery(2),
                BiomeFilter.biome(),
                InSquarePlacement.spread()
        );

        PlacementUtils.register(context, VOID_SHALE_BLOBS, get(context, EnderscapeConfiguredFeatures.VOID_SHALE_BLOB),
                FULL_RANGE,
                CountPlacement.of(24),
                BiomeFilter.biome(),
                InSquarePlacement.spread()
        );

        PlacementUtils.register(context, MIRESTONE_BLOBS, get(context, EnderscapeConfiguredFeatures.MIRESTONE_BLOB),
                FULL_RANGE,
                CountPlacement.of(24),
                BiomeFilter.biome(),
                InSquarePlacement.spread()
        );

        PlacementUtils.register(context, SMALL_ISLANDS, get(context, EnderscapeConfiguredFeatures.ENDERSCAPE_ISLAND),
                RarityFilter.onAverageOnceEvery(14),
                PlacementUtils.countExtra(1, 0.25F, 1),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(55), VerticalAnchor.absolute(70)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, SMALL_SKY_ISLANDS, get(context, EnderscapeConfiguredFeatures.ENDERSCAPE_ISLAND),
                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(230), VerticalAnchor.absolute(260))),
                RarityFilter.onAverageOnceEvery(30),
                InSquarePlacement.spread(),
                BiomeFilter.biome()
        );
    }

    private void registerVeiledWoodlands(BootstrapContext<PlacedFeature> context) {

        PlacementUtils.register(context, VEILED_WOODLANDS_VEGETATION, get(context, EnderscapeConfiguredFeatures.VEILED_WOODLANDS_VEGETATION),
                FULL_RANGE,
                CountOnEveryLayerPlacement.of(UniformInt.of(96, 128)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.VEILED_END_STONE.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, WISP_FLOWER_PATCHES, get(context, EnderscapeConfiguredFeatures.WISP_FLOWER_PATCH),
                FULL_RANGE,
                CountOnEveryLayerPlacement.of(2),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.VEILED_END_STONE.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, VEILED_TREES, get(context, EnderscapeConfiguredFeatures.VEILED_TREE),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(2),
                RarityFilter.onAverageOnceEvery(1),
                BlockPredicateFilter.forPredicate(matchesBlocks(new Vec3i(0, 0, 0), AIR)),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.VEILED_END_STONE.get()), ONLY_IN_AIR_PREDICATE, 12),
                BiomeFilter.biome()
        );
    }

    private void registerMagniaCrags(BootstrapContext<PlacedFeature> context) {
        PlacementUtils.register(context, UPWARD_ALLURING_MAGNIA_SPROUTS, get(context, EnderscapeConfiguredFeatures.UPWARD_ALLURING_MAGNIA_SPROUT),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(12),
                BlockPredicateFilter.forPredicate(
                        allOf(
                                matchesBlocks(new Vec3i(0, -1, 0), EnderscapeBlocks.ALLURING_MAGNIA.get()),
                                matchesBlocks(new Vec3i(0, 1, 0), AIR),
                                matchesBlocks(new Vec3i(0, 0, 0), AIR),
                                matchesBlocks(new Vec3i(0, 0, 1), AIR),
                                matchesBlocks(new Vec3i(0, 0, -1), AIR),
                                matchesBlocks(new Vec3i(1, 0, 0), AIR),
                                matchesBlocks(new Vec3i(-1, 0, 0), AIR)
                        )
                ),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, DOWNWARD_REPULSIVE_MAGNIA_SPROUTS, get(context, EnderscapeConfiguredFeatures.DOWNWARD_REPULSIVE_MAGNIA_SPROUT),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(18),
                EnvironmentScanPlacement.scanningFor(Direction.UP, allOf(hasSturdyFace(Direction.DOWN), matchesBlocks(EnderscapeBlocks.REPULSIVE_MAGNIA.get())), ONLY_IN_AIR_PREDICATE, 32),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, MAGNIA_TOWER, get(context, EnderscapeConfiguredFeatures.MAGNIA_TOWER),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(1),
                RarityFilter.onAverageOnceEvery(3),
                BlockPredicateFilter.forPredicate(
                        allOf(
                                matchesBlocks(new Vec3i(0, 3, 0), AIR),
                                matchesBlocks(new Vec3i(0, 5, 0), AIR),
                                matchesBlocks(new Vec3i(0, 5, 3), AIR),
                                matchesBlocks(new Vec3i(0, 5, 5), AIR),
                                matchesBlocks(new Vec3i(0, 5, -3), AIR),
                                matchesBlocks(new Vec3i(0, 5, -5), AIR),
                                matchesBlocks(new Vec3i(3, 5, 0), AIR),
                                matchesBlocks(new Vec3i(3, 5, 3), AIR),
                                matchesBlocks(new Vec3i(3, 5, -3), AIR),
                                matchesBlocks(new Vec3i(5, 5, 0), AIR),
                                matchesBlocks(new Vec3i(5, 5, 0), AIR),
                                matchesBlocks(new Vec3i(5, 5, 5), AIR),
                                matchesBlocks(new Vec3i(5, 5, -5), AIR),
                                matchesBlocks(new Vec3i(-3, 5, 0), AIR),
                                matchesBlocks(new Vec3i(-3, 5, 3), AIR),
                                matchesBlocks(new Vec3i(-3, 5, -3), AIR),
                                matchesBlocks(new Vec3i(-5, 5, 5), AIR),
                                matchesBlocks(new Vec3i(-5, 5, -5), AIR),
                                not(anyOf(
                                        matchesBlocks(new Vec3i(0, -3, 0), AIR),
                                        matchesBlocks(new Vec3i(0, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(0, -5, 3), AIR),
                                        matchesBlocks(new Vec3i(0, -5, 5), AIR),
                                        matchesBlocks(new Vec3i(0, -5, -3), AIR),
                                        matchesBlocks(new Vec3i(0, -5, -5), AIR),
                                        matchesBlocks(new Vec3i(3, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(3, -5, 3), AIR),
                                        matchesBlocks(new Vec3i(3, -5, -3), AIR),
                                        matchesBlocks(new Vec3i(5, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(5, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(5, -5, 5), AIR),
                                        matchesBlocks(new Vec3i(5, -5, -5), AIR),
                                        matchesBlocks(new Vec3i(-3, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(-3, -5, 3), AIR),
                                        matchesBlocks(new Vec3i(-3, -5, -3), AIR),
                                        matchesBlocks(new Vec3i(-5, -5, 5), AIR),
                                        matchesBlocks(new Vec3i(-5, -5, -5), AIR)
                                ))
                        )
                ),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(END_STONE, EnderscapeBlocks.MIRESTONE.get(), EnderscapeBlocks.ALLURING_MAGNIA.get(), EnderscapeBlocks.REPULSIVE_MAGNIA.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, MAGNIA_ARCH, get(context, EnderscapeConfiguredFeatures.MAGNIA_ARCH),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(1),
                RarityFilter.onAverageOnceEvery(2),
                BlockPredicateFilter.forPredicate(
                        allOf(
                                matchesBlocks(new Vec3i(0, 3, 0), AIR),
                                matchesBlocks(new Vec3i(0, 5, 0), AIR),
                                matchesBlocks(new Vec3i(0, 5, 3), AIR),
                                matchesBlocks(new Vec3i(0, 5, 5), AIR),
                                matchesBlocks(new Vec3i(0, 5, -3), AIR),
                                matchesBlocks(new Vec3i(0, 5, -5), AIR),
                                matchesBlocks(new Vec3i(3, 5, 0), AIR),
                                matchesBlocks(new Vec3i(3, 5, 3), AIR),
                                matchesBlocks(new Vec3i(3, 5, -3), AIR),
                                matchesBlocks(new Vec3i(5, 5, 0), AIR),
                                matchesBlocks(new Vec3i(5, 5, 0), AIR),
                                matchesBlocks(new Vec3i(5, 5, 5), AIR),
                                matchesBlocks(new Vec3i(5, 5, -5), AIR),
                                matchesBlocks(new Vec3i(-3, 5, 0), AIR),
                                matchesBlocks(new Vec3i(-3, 5, 3), AIR),
                                matchesBlocks(new Vec3i(-3, 5, -3), AIR),
                                matchesBlocks(new Vec3i(-5, 5, 5), AIR),
                                matchesBlocks(new Vec3i(-5, 5, -5), AIR),
                                not(anyOf(
                                        matchesBlocks(new Vec3i(0, -3, 0), AIR),
                                        matchesBlocks(new Vec3i(0, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(0, -5, 3), AIR),
                                        matchesBlocks(new Vec3i(0, -5, 5), AIR),
                                        matchesBlocks(new Vec3i(0, -5, -3), AIR),
                                        matchesBlocks(new Vec3i(0, -5, -5), AIR),
                                        matchesBlocks(new Vec3i(3, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(3, -5, 3), AIR),
                                        matchesBlocks(new Vec3i(3, -5, -3), AIR),
                                        matchesBlocks(new Vec3i(5, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(5, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(5, -5, 5), AIR),
                                        matchesBlocks(new Vec3i(5, -5, -5), AIR),
                                        matchesBlocks(new Vec3i(-3, -5, 0), AIR),
                                        matchesBlocks(new Vec3i(-3, -5, 3), AIR),
                                        matchesBlocks(new Vec3i(-3, -5, -3), AIR),
                                        matchesBlocks(new Vec3i(-5, -5, 5), AIR),
                                        matchesBlocks(new Vec3i(-5, -5, -5), AIR)
                                ))
                        )
                ),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(END_STONE, EnderscapeBlocks.MIRESTONE.get(), EnderscapeBlocks.ALLURING_MAGNIA.get(), EnderscapeBlocks.REPULSIVE_MAGNIA.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1)),
                BiomeFilter.biome()
        );
    }

    private void registerCelestialGrove(BootstrapContext<PlacedFeature> context) {
        PlacementUtils.register(context, BULB_FLOWER, get(context, EnderscapeConfiguredFeatures.BULB_FLOWER),
                aboveDepthsRange,
                NoiseBasedCountPlacement.of(80, 10, -0.2),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.CELESTIAL_OVERGROWTH.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1))
        );

        PlacementUtils.register(context, CELESTIAL_GROVE_VEGETATION, get(context, EnderscapeConfiguredFeatures.CELESTIAL_GROVE_VEGETATION),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(12),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.CELESTIAL_OVERGROWTH.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1))
        );

        PlacementUtils.register(context, CELESTIAL_GROWTH, get(context, EnderscapeConfiguredFeatures.CELESTIAL_GROWTH),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(60),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.CELESTIAL_OVERGROWTH.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1))
        );

        PlacementUtils.register(context, LARGE_CELESTIAL_CHANTERELLES, get(context, EnderscapeConfiguredFeatures.LARGE_CELESTIAL_CHANTERELLE),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(1),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.CELESTIAL_OVERGROWTH.get()), ONLY_IN_AIR_PREDICATE, 12),
                BlockPredicateFilter.forPredicate(matchesBlocks(new Vec3i(0, 1, 0), AIR)),
                RandomOffsetPlacement.vertical(ConstantInt.of(1))
        );

        PlacementUtils.register(context, UNCOMMON_MURUBLIGHT_SHELF, get(context, EnderscapeConfiguredFeatures.MURUBLIGHT_SHELF),
                aboveDepthsRange,
                CountPlacement.of(15),
                BiomeFilter.biome(),
                InSquarePlacement.spread()
        );
    }

    private void registerCorruptBarrens(BootstrapContext<PlacedFeature> context) {
        PlacementUtils.register(context, MIRESTONE_PILLARS, get(context, EnderscapeConfiguredFeatures.MIRESTONE_PILLARS),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(60),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.MIRESTONE.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1))
        );

        PlacementUtils.register(context, KURODITE, get(context, EnderscapeConfiguredFeatures.KURODITE),
                FULL_RANGE,
                CountPlacement.of(64),
                BiomeFilter.biome(),
                InSquarePlacement.spread()
        );

        PlacementUtils.register(context, BLINKLIGHT_VINES, get(context, EnderscapeConfiguredFeatures.BLINKLIGHT_VINES),
                aboveDepthsRange,
                CountPlacement.of(12),
                NoiseBasedCountPlacement.of(32, 4, -0.36),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.UP, allOf(hasSturdyFace(Direction.DOWN), matchesBlocks(EnderscapeBlocks.MIRESTONE.get(), EnderscapeBlocks.CORRUPT_OVERGROWTH.get())), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, CORRUPT_BARRENS_VEGETATION, get(context, EnderscapeConfiguredFeatures.CORRUPT_BARRENS_VEGETATION),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(6),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.MIRESTONE.get(), EnderscapeBlocks.CORRUPT_OVERGROWTH.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1))
        );

        PlacementUtils.register(context, DOWNWARD_CORRUPT_GROWTH, get(context, EnderscapeConfiguredFeatures.DOWNWARD_CORRUPT_GROWTH),
                aboveDepthsRange,
                CountPlacement.of(256),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.UP, allOf(hasSturdyFace(Direction.DOWN), matchesBlocks(EnderscapeBlocks.MIRESTONE.get(), EnderscapeBlocks.CORRUPT_OVERGROWTH.get())), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, DOWNWARD_TALL_CORRUPT_GROWTH, get(context, EnderscapeConfiguredFeatures.DOWNWARD_TALL_CORRUPT_GROWTH),
                aboveDepthsRange,
                CountPlacement.of(25),
                NoiseBasedCountPlacement.of(35, 6, 0.0),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.UP, allOf(hasSturdyFace(Direction.DOWN), matchesBlocks(EnderscapeBlocks.MIRESTONE.get(), EnderscapeBlocks.CORRUPT_OVERGROWTH.get())), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        );

        PlacementUtils.register(context, UPWARD_CORRUPT_GROWTH, get(context, EnderscapeConfiguredFeatures.UPWARD_CORRUPT_GROWTH),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(70),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.MIRESTONE.get(), EnderscapeBlocks.CORRUPT_OVERGROWTH.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1))
        );

        PlacementUtils.register(context, UPWARD_TALL_CORRUPT_GROWTH, get(context, EnderscapeConfiguredFeatures.UPWARD_TALL_CORRUPT_GROWTH),
                aboveDepthsRange,
                CountOnEveryLayerPlacement.of(10),
                NoiseBasedCountPlacement.of(16, 4, -0.36),
                BiomeFilter.biome(),
                InSquarePlacement.spread(),
                EnvironmentScanPlacement.scanningFor(Direction.DOWN, matchesBlocks(EnderscapeBlocks.MIRESTONE.get(), EnderscapeBlocks.CORRUPT_OVERGROWTH.get()), ONLY_IN_AIR_PREDICATE, 12),
                RandomOffsetPlacement.vertical(ConstantInt.of(1))
        );

        PlacementUtils.register(context, LARGE_MURUBLIGHT_CHANTERELLES, get(context, EnderscapeConfiguredFeatures.DOWNWARD_LARGE_MURUBLIGHT_CHANTERELLE),
                aboveDepthsRange,
                CountPlacement.of(8),
                InSquarePlacement.spread(),
                PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                EnvironmentScanPlacement.scanningFor(Direction.UP, allOf(hasSturdyFace(Direction.DOWN), matchesBlocks(EnderscapeBlocks.CORRUPT_OVERGROWTH.get())), ONLY_IN_AIR_PREDICATE, 12),
                BlockPredicateFilter.forPredicate(matchesBlocks(new Vec3i(0, -1, 0), AIR)),
                RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome()
        );
    }

    private Holder.Reference<ConfiguredFeature<?, ?>> get(BootstrapContext<PlacedFeature> context, ResourceKey<ConfiguredFeature<?, ?>> key) {
        return context.lookup(Registries.CONFIGURED_FEATURE).getOrThrow(key);
    }

    private static ResourceKey<PlacedFeature> register(String name) {
        ResourceKey<PlacedFeature> key = ResourceKey.create(Registries.PLACED_FEATURE, Enderscape.id(name));
        PLACED_FEATURES.add(key);
        return key;
    }
}
