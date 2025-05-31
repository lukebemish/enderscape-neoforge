package net.bunten.enderscape.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record LargeCelestialChanterelleConfig(IntProvider height, float cap_radius_division, float cap_droop_percentage, float excess_vine_discard_chance, int vine_generation_tries, int tries) implements FeatureConfiguration {
    public static final Codec<LargeCelestialChanterelleConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            (IntProvider.codec(1, 64).fieldOf("height")).forGetter(config -> config.height),
            (Codec.floatRange(1, 64).fieldOf("cap_radius_division")).forGetter(config -> config.cap_radius_division),
            (Codec.floatRange(0, 1).fieldOf("cap_droop_percentage")).forGetter(config -> config.cap_droop_percentage),
            (Codec.floatRange(0, 1).fieldOf("excess_vine_discard_chance")).forGetter(config -> config.excess_vine_discard_chance),
            (Codec.intRange(0, 256).fieldOf("vine_generation_tries")).forGetter(config -> config.vine_generation_tries),
            (Codec.intRange(1, 64).fieldOf("tries")).forGetter(config -> config.tries))
            .apply(instance, LargeCelestialChanterelleConfig::new));

    public static final LargeCelestialChanterelleConfig DEFAULT = new LargeCelestialChanterelleConfig(UniformInt.of(10, 35), 4, 0.75F, 1, 64, 16);
}