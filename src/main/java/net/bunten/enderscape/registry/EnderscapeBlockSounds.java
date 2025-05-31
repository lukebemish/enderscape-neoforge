package net.bunten.enderscape.registry;

import net.bunten.enderscape.Enderscape;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.util.List;
import java.util.function.Supplier;

public class EnderscapeBlockSounds {

    public static final SoundEvent ALLURING_MAGNIA_IDLE = register("alluring_magnia.idle");
    public static final SoundEvent ALLURING_MAGNIA_SPROUT_MOVE = register("alluring_magnia_sprout.move");
    public static final SoundEvent ALLURING_MAGNIA_SPROUT_OVERHEAT = register("alluring_magnia_sprout.overheat");
    public static final SoundEvent ALLURING_MAGNIA_SPROUT_POWER_OFF = register("alluring_magnia_sprout.power_off");
    public static final SoundEvent ALLURING_MAGNIA_SPROUT_POWER_ON = register("alluring_magnia_sprout.power_on");
    public static final SoundEvent BLINKLAMP_DECREASE = register("blinklamp.decrease");
    public static final SoundEvent BLINKLAMP_INCREASE = register("blinklamp.increase");
    public static final SoundEvent BLINKLIGHT_VINES_BLINK = register("blinklight_vines.blink");
    public static final SoundEvent BLINKLIGHT_VINES_INBETWEEN = register("blinklight_vines.inbetween");
    public static final SoundEvent CELESTIAL_BUTTON_CLICK_OFF = register("celestial_button.click_off");
    public static final SoundEvent CELESTIAL_BUTTON_CLICK_ON = register("celestial_button.click_on");
    public static final SoundEvent CELESTIAL_CHANTERELLE_CORRUPT = register("celestial_chanterelle.corrupt");
    public static final SoundEvent CELESTIAL_DOOR_CLOSE = register("celestial_door.close");
    public static final SoundEvent CELESTIAL_DOOR_OPEN = register("celestial_door.open");
    public static final SoundEvent CELESTIAL_FENCE_GATE_CLOSE = register("celestial_fence_gate.close");
    public static final SoundEvent CELESTIAL_FENCE_GATE_OPEN = register("celestial_fence_gate.open");
    public static final SoundEvent CELESTIAL_GROWTH_IDLE = register("celestial_growth.idle");
    public static final SoundEvent CELESTIAL_OVERGROWTH_FLATTEN = register("celestial_overgrowth.flatten");
    public static final SoundEvent CELESTIAL_PRESSURE_PLATE_CLICK_OFF = register("celestial_pressure_plate.click_off");
    public static final SoundEvent CELESTIAL_PRESSURE_PLATE_CLICK_ON = register("celestial_pressure_plate.click_on");
    public static final SoundEvent CELESTIAL_TRAPDOOR_CLOSE = register("celestial_trapdoor.close");
    public static final SoundEvent CELESTIAL_TRAPDOOR_OPEN = register("celestial_trapdoor.open");
    public static final SoundEvent CHORUS_FLOWER_IDLE = register("chorus_flower.idle");
    public static final SoundEvent CHORUS_CAKE_ROLL_EAT = register("chorus_cake_roll.eat");
    public static final SoundEvent CORRUPT_GROWTH_IDLE = register("corrupt_growth.idle");
    public static final SoundEvent CORRUPT_OVERGROWTH_FLATTEN = register("corrupt_overgrowth.flatten");
    public static final SoundEvent DRIFT_JELLY_BOUNCE = register("drift_jelly.bounce");
    public static final SoundEvent END_PORTAL_TRAVEL = register("end_portal.travel");
    public static final SoundEvent END_VAULT_ACTIVATE = register("end_vault.activate");
    public static final SoundEvent END_VAULT_AMBIENT = register("end_vault.ambient");
    public static final SoundEvent END_VAULT_CLOSE_SHUTTER = register("end_vault.close_shutter");
    public static final SoundEvent END_VAULT_DEACTIVATE = register("end_vault.deactivate");
    public static final SoundEvent END_VAULT_EJECT_ITEM = register("end_vault.eject_item");
    public static final SoundEvent END_VAULT_INSERT_ITEM = register("end_vault.insert_item");
    public static final SoundEvent END_VAULT_INSERT_ITEM_FAIL = register("end_vault.insert_item_fail");
    public static final SoundEvent END_VAULT_OPEN_SHUTTER = register("end_vault.open_shutter");
    public static final SoundEvent END_VAULT_REJECT_REWARDED_PLAYER = register("end_vault.reject_rewarded_player");
    public static final SoundEvent MURUBLIGHT_BUTTON_CLICK_OFF = register("murublight_button.click_off");
    public static final SoundEvent MURUBLIGHT_BUTTON_CLICK_ON = register("murublight_button.click_on");
    public static final SoundEvent MURUBLIGHT_CHANTERELLE_PURIFY = register("murublight_chanterelle.purify");
    public static final SoundEvent MURUBLIGHT_DOOR_CLOSE = register("murublight_door.close");
    public static final SoundEvent MURUBLIGHT_DOOR_OPEN = register("murublight_door.open");
    public static final SoundEvent MURUBLIGHT_FENCE_GATE_CLOSE = register("murublight_fence_gate.close");
    public static final SoundEvent MURUBLIGHT_FENCE_GATE_OPEN = register("murublight_fence_gate.open");
    public static final SoundEvent MURUBLIGHT_PRESSURE_PLATE_CLICK_OFF = register("murublight_pressure_plate.click_off");
    public static final SoundEvent MURUBLIGHT_PRESSURE_PLATE_CLICK_ON = register("murublight_pressure_plate.click_on");
    public static final SoundEvent MURUBLIGHT_TRAPDOOR_CLOSE = register("murublight_trapdoor.close");
    public static final SoundEvent MURUBLIGHT_TRAPDOOR_OPEN = register("murublight_trapdoor.open");
    public static final SoundEvent NEBULITE_ORE_IDLE = Enderscape.registerSoundEvent("block.nebulite_ore.idle");
    public static final SoundEvent NEBULITE_ORE_IDLE_FAR = Enderscape.registerSoundEvent("block.nebulite_ore.idle.far");
    public static final SoundEvent NEBULITE_ORE_IDLE_OBSTRUCTED = Enderscape.registerSoundEvent("block.nebulite_ore.idle.obstructed");
    public static final SoundEvent REPULSIVE_MAGNIA_IDLE = register("repulsive_magnia.idle");
    public static final SoundEvent REPULSIVE_MAGNIA_SPROUT_MOVE = register("repulsive_magnia_sprout.move");
    public static final SoundEvent REPULSIVE_MAGNIA_SPROUT_OVERHEAT = register("repulsive_magnia_sprout.overheat");
    public static final SoundEvent REPULSIVE_MAGNIA_SPROUT_POWER_OFF = register("repulsive_magnia_sprout.power_off");
    public static final SoundEvent REPULSIVE_MAGNIA_SPROUT_POWER_ON = register("repulsive_magnia_sprout.power_on");
    public static final SoundEvent VEILED_BUTTON_CLICK_OFF = register("veiled_button.click_off");
    public static final SoundEvent VEILED_BUTTON_CLICK_ON = register("veiled_button.click_on");
    public static final SoundEvent VEILED_DOOR_CLOSE = register("veiled_door.close");
    public static final SoundEvent VEILED_DOOR_OPEN = register("veiled_door.open");
    public static final SoundEvent VEILED_FENCE_GATE_CLOSE = register("veiled_fence_gate.close");
    public static final SoundEvent VEILED_FENCE_GATE_OPEN = register("veiled_fence_gate.open");
    public static final SoundEvent VEILED_PRESSURE_PLATE_CLICK_OFF = register("veiled_pressure_plate.click_off");
    public static final SoundEvent VEILED_PRESSURE_PLATE_CLICK_ON = register("veiled_pressure_plate.click_on");
    public static final SoundEvent VEILED_TRAPDOOR_CLOSE = register("veiled_trapdoor.close");
    public static final SoundEvent VEILED_TRAPDOOR_OPEN = register("veiled_trapdoor.open");
    public static final SoundEvent VOID_SHALE_SHATTER_HIGH = register("void_shale.shatter_high");
    public static final SoundEvent VOID_SHALE_SHATTER_LOW = register("void_shale.shatter_low");
    public static final SoundEvent VOID_SHALE_SHATTER_MEDIUM = register("void_shale.shatter_medium");
    public static final List<SoundEvent> VOID_SHALE_SHATTER_SOUNDS = List.of(SoundEvents.EMPTY, VOID_SHALE_SHATTER_LOW, VOID_SHALE_SHATTER_MEDIUM, VOID_SHALE_SHATTER_HIGH);

    private static SoundEvent register(String name) {
        return Enderscape.registerSoundEvent("block." + name);
    }
}
