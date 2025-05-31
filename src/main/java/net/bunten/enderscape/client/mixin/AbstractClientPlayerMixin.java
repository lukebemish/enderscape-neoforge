package net.bunten.enderscape.client.mixin;

import com.mojang.authlib.GameProfile;
import net.bunten.enderscape.EnderscapeConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {

    public AbstractClientPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Unique
    private int Enderscape$elytraFlightTime = 0;

    @Unique
    private float Enderscape$getElytraFovIntensity() {
        float configIntensity = (float) EnderscapeConfig.getInstance().elytraFovEffectIntensity.getAsInt() / 100.0F;
        return (float) (configIntensity * Minecraft.getInstance().options.fovEffectScale().get());
    }

    @Unique
    private float Enderscape$getElytraFOV() {
        float fovModifier = 1.0F;

        if (isFallFlying()) {
            Enderscape$elytraFlightTime++;

            if (isRemoved() || (Enderscape$elytraFlightTime > 20 && !isFallFlying())) return fovModifier;

            float speed = (float) getDeltaMovement().lengthSqr();
            float factor = Mth.clamp(speed / 20, 0, 1.5F) * Enderscape$getElytraFovIntensity();

            if (Enderscape$elytraFlightTime < 20) {
                return fovModifier;
            } else if (Enderscape$elytraFlightTime < 40) {
                fovModifier = 1.0F + ((float) (Enderscape$elytraFlightTime - 20) / 20.0F) * factor;
            } else {
                fovModifier = 1.0F + factor;
            }
        }

        return Mth.clamp(fovModifier, 1.0F, 1.5F);
    }

    @Inject(method = "getFieldOfViewModifier", at = @At("RETURN"), cancellable = true)
    private void getFieldOfViewModifier(CallbackInfoReturnable<Float> info) {
        if (EnderscapeConfig.getInstance().elytraAddFovEffects.get()) info.setReturnValue(info.getReturnValue() * Enderscape$getElytraFOV());
    }
}