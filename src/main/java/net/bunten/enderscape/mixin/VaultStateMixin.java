package net.bunten.enderscape.mixin;

import net.bunten.enderscape.registry.EnderscapeBlockSounds;
import net.bunten.enderscape.registry.EnderscapeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.vault.VaultState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VaultState.class)
public abstract class VaultStateMixin {

    @Redirect(method = "ejectResultItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void ejectResultItem(ServerLevel level, Player player, BlockPos pos, SoundEvent sound, SoundSource source, float a, float b) {
        if (level.getBlockState(pos).is(EnderscapeBlocks.END_VAULT.get())) {
            level.playSound(player, pos, EnderscapeBlockSounds.END_VAULT_EJECT_ITEM, source, a, b);
        } else {
            level.playSound(player, pos, sound, source, a, b);
        }
    }
}