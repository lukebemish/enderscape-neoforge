package net.bunten.enderscape.registry;

import net.bunten.enderscape.Enderscape;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimPattern;

import java.util.ArrayList;
import java.util.List;

public class EnderscapeTrimPatterns {

    public static final List<ResourceKey<TrimPattern>> TRIM_PATTERNS = new ArrayList<>();

    public static final ResourceKey<TrimPattern> STASIS = register("stasis");

    public void bootstrap(BootstrapContext<TrimPattern> context) {
        register(context, EnderscapeItems.STASIS_ARMOR_TRIM_SMITHING_TEMPLATE.get(), STASIS);
    }

    public static void register(BootstrapContext<TrimPattern> bootstrapContext, Item item, ResourceKey<TrimPattern> key) {
        TrimPattern pattern = new TrimPattern(
                key.location(),
                BuiltInRegistries.ITEM.wrapAsHolder(item),
                Component.translatable(Util.makeDescriptionId("trim_pattern", key.location())),
                false
        );

        bootstrapContext.register(key, pattern);
    }

    private static ResourceKey<TrimPattern> register(String name) {
        ResourceKey<TrimPattern> key = ResourceKey.create(Registries.TRIM_PATTERN, Enderscape.id(name));
        TRIM_PATTERNS.add(key);
        return key;
    }
}