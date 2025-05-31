package net.bunten.enderscape.registry;

import net.bunten.enderscape.block.sound.SoundTypeOverride;
import net.bunten.enderscape.EnderscapeConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.Predicate;

public class EnderscapeSoundTypeOverrides {

	private static final EnderscapeConfig CONFIG = EnderscapeConfig.getInstance();

	public static final List<SoundTypeOverride> SOUND_TYPE_OVERRIDES = new ArrayList<>();
	private static final List<BlockState> IGNORED_STATES = new ArrayList<>();
	private static final Map<BlockState, SoundType> SOUND_TYPE_CACHE = new IdentityHashMap<>();

	public static Optional<SoundType> getSoundType(BlockState state) {
		if (IGNORED_STATES.contains(state)) return Optional.empty();

		return Optional.ofNullable(SOUND_TYPE_CACHE.computeIfAbsent(state, s -> {
			for (SoundTypeOverride override : SOUND_TYPE_OVERRIDES) if (override.applies(s)) return override.getSoundType();
			IGNORED_STATES.add(s);
			return null;
		}));
	}

	public static void register(SoundTypeOverride override) {
		Objects.requireNonNull(override);
		SOUND_TYPE_OVERRIDES.add(override);
	}

	public static void register(SoundTypeOverride... overrides) {
		for (SoundTypeOverride override : overrides) register(override);
	}

	private static String getNameOf(BlockState state) {
		return BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
	}

	static {
		Map<SoundType, Predicate<BlockState>> overrides = Map.of(
				EnderscapeSoundTypes.CHORUS_PLANT, (state) -> CONFIG.blockSoundsUpdateChorus.get() && getNameOf(state).equals("chorus_plant"),
				EnderscapeSoundTypes.CHORUS_FLOWER, (state) -> CONFIG.blockSoundsUpdateChorus.get() && getNameOf(state).equals("chorus_flower"),
				EnderscapeSoundTypes.PURPUR, (state) -> CONFIG.blockSoundsUpdatePurpur.get() && getNameOf(state).contains("purpur"),
				EnderscapeSoundTypes.END_PORTAL_FRAME, (state) -> CONFIG.blockSoundsUpdateEndPortalFrame.get() && getNameOf(state).equals("end_portal_frame"),
				EnderscapeSoundTypes.END_PORTAL, (state) -> CONFIG.blockSoundsUpdateEndPortals.get() && getNameOf(state).equals("end_portal"),
				EnderscapeSoundTypes.END_GATEWAY, (state) -> CONFIG.blockSoundsUpdateEndPortals.get() && getNameOf(state).equals("end_gateway"),
				EnderscapeSoundTypes.END_ROD, (state) -> CONFIG.blockSoundsUpdateEndRods.get() && getNameOf(state).equals("end_rod"),
				EnderscapeSoundTypes.END_STONE, (state) -> CONFIG.blockSoundsUpdateEndStone.get() && getNameOf(state).contains("end_stone") && !getNameOf(state).contains("brick") && !getNameOf(state).contains("chiseled")&& !getNameOf(state).contains("veiled"),
				EnderscapeSoundTypes.END_STONE_BRICKS, (state) -> CONFIG.blockSoundsUpdateEndStoneBricks.get() && getNameOf(state).contains("end_stone") && (getNameOf(state).contains("brick") || getNameOf(state).contains("chiseled")),
				EnderscapeSoundTypes.SHULKER_BOX, (state) -> CONFIG.blockSoundsUpdateShulkerBoxes.get() && state.getBlock() instanceof ShulkerBoxBlock
		);

		overrides.forEach((sound, condition) -> register(new SoundTypeOverride(sound, condition)));
	}
}