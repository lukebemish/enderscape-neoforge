package net.bunten.enderscape.datagen;

import net.bunten.enderscape.Enderscape;
import net.bunten.enderscape.criteria.BounceOnDrifterCriterion;
import net.bunten.enderscape.criteria.MirrorTeleportCriterion;
import net.bunten.enderscape.criteria.PullEntityCriterion;
import net.bunten.enderscape.registry.EnderscapeBiomes;
import net.bunten.enderscape.registry.EnderscapeCriteria;
import net.bunten.enderscape.registry.EnderscapeDataComponents;
import net.bunten.enderscape.registry.EnderscapeEntities;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Optional;

import static net.bunten.enderscape.registry.EnderscapeBlocks.CELESTIAL_OVERGROWTH;
import static net.bunten.enderscape.registry.EnderscapeBlocks.END_VAULT;
import static net.bunten.enderscape.registry.EnderscapeItems.*;
import static net.minecraft.world.item.Items.BUCKET;
import static net.minecraft.world.item.Items.FIREWORK_ROCKET;

public class EnderscapeAdvancementProvider extends AdvancementProvider {

    public static final List<ResourceKey<Biome>> EXPLORE_END_BIOMES = List.of(
            Biomes.THE_END, Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS, Biomes.SMALL_END_ISLANDS,
            EnderscapeBiomes.VEILED_WOODLANDS, EnderscapeBiomes.MAGNIA_CRAGS,
            EnderscapeBiomes.CELESTIAL_GROVE, EnderscapeBiomes.CORRUPT_BARRENS,
            EnderscapeBiomes.VOID_DEPTHS, EnderscapeBiomes.VOID_SKIES, EnderscapeBiomes.VOID_SKY_ISLANDS
    );

    @SuppressWarnings("removal")
    public EnderscapeAdvancementProvider(GatherDataEvent event) {
        super(event.getGenerator().getPackOutput(), event.getLookupProvider(), event.getExistingFileHelper(), List.of((provider, consumer, existingFileHelper) -> {
            HolderGetter<Item> itemRegistry = provider.lookupOrThrow(Registries.ITEM);
            HolderGetter<Block> blockRegistry = provider.lookupOrThrow(Registries.BLOCK);
            HolderLookup.RegistryLookup<EntityType<?>> entityRegistry = provider.lookupOrThrow(Registries.ENTITY_TYPE);

            ResourceKey<Advancement> endGatewayKey = ResourceKey.create(Registries.ADVANCEMENT, ResourceLocation.withDefaultNamespace("end/enter_end_gateway"));
            ResourceKey<Advancement> findEndCityKey = ResourceKey.create(Registries.ADVANCEMENT, ResourceLocation.withDefaultNamespace("end/find_end_city"));
            ResourceKey<Advancement> elytraKey = ResourceKey.create(Registries.ADVANCEMENT, ResourceLocation.withDefaultNamespace("end/elytra"));

            ItemStack glintMirror = MIRROR.get().getDefaultInstance();
            glintMirror.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);

            AdvancementHolder rustleBucket = Advancement.Builder.advancement()
                    .parent(endGatewayKey.location())
                    .display(
                            RUSTLE_BUCKET.get(),
                            Component.translatable("advancement.enderscape.rustle_bucket"),
                            Component.translatable("advancement.enderscape.rustle_bucket.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion(
                            BuiltInRegistries.ITEM.getKey(RUSTLE_BUCKET.get()).getPath(),
                            PlayerInteractTrigger.TriggerInstance.itemUsedOnEntity(
                                    Optional.empty(),
                                    ItemPredicate.Builder.item().of(BUCKET),
                                    Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().of(EnderscapeEntities.RUSTLE.get())))
                            )
                    )
                    .save(consumer, Enderscape.id("rustle_bucket").toString());

            AdvancementHolder unlockEndVault = Advancement.Builder.advancement()
                    .parent(findEndCityKey.location())
                    .display(
                            END_CITY_KEY.get(),
                            Component.translatable("advancement.enderscape.unlock_end_vault"),
                            Component.translatable("advancement.enderscape.unlock_end_vault.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, true
                    )
                    .addCriterion(
                            "unlock_end_vault",
                            ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(
                                    LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(END_VAULT.get())),
                                    ItemPredicate.Builder.item().of(END_CITY_KEY.get())
                            )
                    )
                    .save(consumer, Enderscape.id("unlock_end_vault").toString());

            Advancement.Builder exploreEnd = Advancement.Builder.advancement()
                    .parent(endGatewayKey.location())
                    .display(CELESTIAL_OVERGROWTH.get(),
                            Component.translatable("advancement.enderscape.explore_end"),
                            Component.translatable("advancement.enderscape.explore_end.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false)
                    .rewards(AdvancementRewards.Builder.experience(1000));

            EXPLORE_END_BIOMES.forEach(biome ->
                    exploreEnd.addCriterion(
                            biome.location().toString(),
                            PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(provider.lookupOrThrow(Registries.BIOME).getOrThrow(biome)))
                    )
            );

            exploreEnd.save(consumer, Enderscape.id("explore_end").toString());

            AdvancementHolder obtainNebulite = Advancement.Builder.advancement()
                    .parent(endGatewayKey.location())
                    .display(
                            NEBULITE.get(),
                            Component.translatable("advancement.enderscape.obtain_nebulite"),
                            Component.translatable("advancement.enderscape.obtain_nebulite.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("obtained", RecipeCraftedTrigger.TriggerInstance.craftedItem(Enderscape.id("nebulite_from_shards")))
                    .save(consumer, Enderscape.id("craft_nebulite").toString());

            AdvancementHolder bottleDriftJelly = Advancement.Builder.advancement()
                    .parent(endGatewayKey.location())
                    .display(
                            DRIFT_JELLY_BOTTLE.get(),
                            Component.translatable("advancement.enderscape.bottle_drift_jelly"),
                            Component.translatable("advancement.enderscape.bottle_drift_jelly.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("obtained", InventoryChangeTrigger.TriggerInstance.hasItems(DRIFT_JELLY_BOTTLE.get()))
                    .save(consumer, Enderscape.id("bottle_drift_jelly").toString());

            AdvancementHolder driftLeggings = Advancement.Builder.advancement()
                    .parent(bottleDriftJelly)
                    .display(
                            DRIFT_LEGGINGS.get(),
                            Component.translatable("advancement.enderscape.drift_leggings"),
                            Component.translatable("advancement.enderscape.drift_leggings.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("obtained", InventoryChangeTrigger.TriggerInstance.hasItems(DRIFT_LEGGINGS.get()))
                    .save(consumer, Enderscape.id("drift_leggings").toString());

            AdvancementHolder glideOntoDrifter = Advancement.Builder.advancement()
                    .parent(driftLeggings)
                    .display(
                            FIREWORK_ROCKET.getDefaultInstance(),
                            Component.translatable("advancement.enderscape.glide_onto_drifter"),
                            Component.translatable("advancement.enderscape.glide_onto_drifter.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .addCriterion("bounced", EnderscapeCriteria.BOUNCE_ON_DRIFTER.createCriterion(new BounceOnDrifterCriterion.Conditions(
                            Optional.of(
                                    ContextAwarePredicate.create(
                                            LootItemEntityPropertyCondition.hasProperties(
                                                    LootContext.EntityTarget.THIS,
                                                    EntityPredicate.Builder
                                                            .entity()
                                                            .moving(MovementPredicate.speed(MinMaxBounds.Doubles.atLeast(40)))
                                                            .flags(EntityFlagsPredicate.Builder.flags().setIsFlying(true))
                                            ).build()
                                    )
                            ),
                            Optional.empty()
                    )))
                    .save(consumer, Enderscape.id("glide_onto_drifter").toString());

            AdvancementHolder mirrorTeleport = Advancement.Builder.advancement()
                    .parent(obtainNebulite)
                    .display(
                            glintMirror,
                            Component.translatable("advancement.enderscape.mirror_teleport"),
                            Component.translatable("advancement.enderscape.mirror_teleport.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("teleported", EnderscapeCriteria.MIRROR_TELEPORT.createCriterion(new MirrorTeleportCriterion.Conditions(
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty()
                    )))
                    .save(consumer, Enderscape.id("mirror_teleport").toString());

            AdvancementHolder longDistance = Advancement.Builder.advancement()
                    .parent(mirrorTeleport)
                    .display(
                            glintMirror,
                            Component.translatable("advancement.enderscape.long_distance"),
                            Component.translatable("advancement.enderscape.long_distance.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .rewards(AdvancementRewards.Builder.experience(50))
                    .addCriterion("teleported", EnderscapeCriteria.MIRROR_TELEPORT.createCriterion(new MirrorTeleportCriterion.Conditions(
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.of(
                                    DistancePredicate.horizontal(MinMaxBounds.Doubles.atLeast(2000))
                            ),
                            Optional.of(false)
                    )))
                    .save(consumer, Enderscape.id("long_distance").toString());

            Advancement.Builder.advancement()
                    .parent(longDistance)
                    .display(
                            glintMirror,
                            Component.translatable("advancement.enderscape.transdimensional"),
                            Component.translatable("advancement.enderscape.transdimensional.description"),
                            null,
                            AdvancementType.CHALLENGE,
                            true, true, false
                    )
                    .rewards(AdvancementRewards.Builder.experience(100))
                    .addCriterion("teleported", EnderscapeCriteria.MIRROR_TELEPORT.createCriterion(new MirrorTeleportCriterion.Conditions(
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.of(true)
                    )))
                    .save(consumer, Enderscape.id("transdimensional").toString());

            ItemStack attractor = MAGNIA_ATTRACTOR.get().getDefaultInstance();
            attractor.set(EnderscapeDataComponents.CURRENT_NEBULITE_FUEL, 1);

            AdvancementHolder pullItemWithAttractor = Advancement.Builder.advancement()
                    .parent(obtainNebulite)
                    .display(
                            attractor,
                            Component.translatable("advancement.enderscape.pull_item_with_attractor"),
                            Component.translatable("advancement.enderscape.pull_item_with_attractor.description"),
                            null,
                            AdvancementType.TASK,
                            true, true, false
                    )
                    .addCriterion("pulled_item", EnderscapeCriteria.PULL_ENTITY.createCriterion(new PullEntityCriterion.Conditions(
                            Optional.empty(),
                            Optional.of(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(EntityType.ITEM)).build()),
                            Optional.empty()
                    )))
                    .save(consumer, Enderscape.id("pull_item_with_attractor").toString());
        }));
    }
}
