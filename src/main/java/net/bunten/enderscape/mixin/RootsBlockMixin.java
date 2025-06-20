package net.bunten.enderscape.mixin;

import net.bunten.enderscape.registry.tag.EnderscapeBlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RootsBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(RootsBlock.class)
public abstract class RootsBlockMixin extends BlockBehaviour {
    public RootsBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(at = @At("HEAD"), method = "mayPlaceOn", cancellable = true)
    protected void mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (floor.is(EnderscapeBlockTags.OVERGROWTH_BLOCKS)) {
            info.setReturnValue(true);
        }
    }
}