package net.bunten.enderscape.mixin;

import net.bunten.enderscape.registry.EnderscapeBlockSounds;
import net.bunten.enderscape.registry.EnderscapeBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.entity.vault.VaultBlockEntity;
import net.minecraft.world.level.block.entity.vault.VaultServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VaultBlockEntity.Server.class)
public abstract class VaultBlockEntityServerMixin {

    @Inject(at = @At("HEAD"), method = "playInsertFailSound", cancellable = true)
    private static void getStateWithConnections(ServerLevel level, VaultServerData data, BlockPos pos, SoundEvent sound, CallbackInfo info) {
        if (level.getBlockState(pos).is(EnderscapeBlocks.END_VAULT.get())) {
            if (sound == SoundEvents.VAULT_INSERT_ITEM_FAIL) {
                sound = EnderscapeBlockSounds.END_VAULT_INSERT_ITEM_FAIL;
            } else  if (sound == SoundEvents.VAULT_REJECT_REWARDED_PLAYER) {
                sound = EnderscapeBlockSounds.END_VAULT_REJECT_REWARDED_PLAYER;
            }
        }
    }
}