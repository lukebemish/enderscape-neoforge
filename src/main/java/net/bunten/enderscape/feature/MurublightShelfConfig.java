package net.bunten.enderscape.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.bunten.enderscape.block.MurublightShelfBlock;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record MurublightShelfConfig(int horizontal_range, int vertical_range, int age, int tries) implements FeatureConfiguration {
    public static final Codec<MurublightShelfConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            (Codec.intRange(1, 64).fieldOf("horizontal_range")).forGetter(config -> config.horizontal_range),
            (Codec.intRange(1, 64).fieldOf("vertical_range")).forGetter(config -> config.vertical_range),
            (Codec.intRange(1, MurublightShelfBlock.MAX_AGE).fieldOf("age")).forGetter(config -> config.age),
            (Codec.intRange(1, 512).fieldOf("tries")).forGetter(config -> config.tries))
            .apply(instance, MurublightShelfConfig::new));
}