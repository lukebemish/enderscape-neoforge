package net.bunten.enderscape.mixin;

import net.bunten.enderscape.EnderscapeConfig;
import net.bunten.enderscape.registry.EnderscapeItemSounds;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(EnderpearlItem.class)
public abstract class EnderpearlItemMixin extends Item {

    public EnderpearlItemMixin(Properties properties) {
        super(properties);
    }

    @ModifyArgs(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private void Enderscape$playSound(Args args) {
        if (EnderscapeConfig.getInstance().enderPearlUpdateThrowSound.get()) {
            args.set(4, EnderscapeItemSounds.ENDER_PEARL_THROW.get());
            args.set(6, 1F);
            args.set(7, 1F);
        }
    }
}