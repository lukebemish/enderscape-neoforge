package net.bunten.enderscape.registry;

import net.bunten.enderscape.Enderscape;
import net.bunten.enderscape.effect.LowGravityEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.function.Supplier;

public class EnderscapeMobEffects {

    public static final Holder<MobEffect> LOW_GRAVITY = register("low_gravity", () -> new LowGravityEffect()
            .addAttributeModifier(Attributes.GRAVITY, Enderscape.id("effect.drift.gravity"), -0.4, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            .addAttributeModifier(Attributes.SAFE_FALL_DISTANCE, Enderscape.id("effect.drift.fall_damage_multiplier"), 0.6, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
    );

    private static Holder<MobEffect> register(String name, Supplier<MobEffect> effect) {
        return RegistryHelper.registerForHolder(BuiltInRegistries.MOB_EFFECT, Enderscape.id(name), effect);
    }
}